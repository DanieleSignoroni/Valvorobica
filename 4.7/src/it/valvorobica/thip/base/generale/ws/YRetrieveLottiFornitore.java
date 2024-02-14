package it.valvorobica.thip.base.generale.ws;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.thera.thermfw.base.Trace;

import it.thera.thip.base.articolo.ArticoloFornitoreTM;
import it.thera.thip.base.articolo.ArticoloTM;
import it.thera.thip.base.documentoDgt.DocumentoDigitaleTM;
import it.thera.thip.magazzino.generalemag.LottoTM;
import it.thera.thip.magazzino.movimenti.CausaleMovimentoMagazzino;
import it.thera.thip.magazzino.movimenti.MovimentoMagazzinoTM;
import it.thera.thip.ws.GenRequestJSON;
import it.valvorobica.thip.base.articolo.YMicroFamigliaTM;
import it.valvorobica.thip.base.fornitore.YFornitoreAcquistoTM;
import it.valvorobica.thip.base.portal.YUserPortalSession;
import it.valvorobica.thip.magazzino.generalemag.YLottoTM;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 13/02/2024
 * <br><br>
 * <b></b>    <p></p>
 */

public class YRetrieveLottiFornitore extends GenRequestJSON{

	protected YUserPortalSession userPortal = null;

	public YUserPortalSession getUserPortal() {
		return userPortal;
	}

	public void setUserPortal(YUserPortalSession userPortal) {
		this.userPortal = userPortal;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Map execute(Map m) {
		List errors = new ArrayList();
		if(getUserPortal() != null) {
			if(getUserPortal().getIdFornitore() != null){
				m.put("dati", datiPagina(recuperaLotti(getUserPortal().getIdFornitore(), "CERT_F02")));
			}else {
				errors.add("Utente non abilitato all'estrazione");
			}
		}else {
			errors.add("User portal non valorizzato!");
		}
		m.put("errors", errors);
		return m;
	}

	protected JSONArray datiPagina(List<LottoDaRs> lotti) {
		JSONArray datiPagina = new JSONArray();
		Connection connection = this.getConnectionDescriptor().getConnection();
		for (Iterator<LottoDaRs> iterator = lotti.iterator(); iterator.hasNext();) {
			LottoDaRs lotto = (LottoDaRs) iterator.next();
			PreparedStatement ps = null;
			try (PreparedStatement preparedStatement = connection.prepareStatement(this.getStmtEstrazioneDatiPagina(
					lotto.getIdAzienda(), lotto.getIdArticolo(), lotto.getIdLotto()));
					ResultSet rs = preparedStatement.executeQuery()) {
				while(rs.next()) {
					JSONObject dato = new JSONObject();
					dato.put("data_bolla", rs.getDate(MovimentoMagazzinoTM.DATA_BOLLA));	
					dato.put("numero_bolla", rs.getString(MovimentoMagazzinoTM.NUMERO_BOLLA));
					dato.put("riga_bolla", rs.getInt(MovimentoMagazzinoTM.NUM_RIGA_DOC));
					dato.put("lotto_acquisto", rs.getString(LottoTM.LOTTO_ACQUISTO) != null ? rs.getString(LottoTM.LOTTO_ACQUISTO) : "Non compilato");
					dato.put("articolo_fornitore", rs.getString(ArticoloFornitoreTM.ARTICOLO_FOR) != null ? rs.getString(ArticoloFornitoreTM.ARTICOLO_FOR) : "Non compilato");
					dato.put("articolo", lotto.getIdArticolo());
					dato.put("descrizione_estesa", rs.getString(ArticoloTM.DESC_ESTESA));
					dato.put("quantita", rs.getString(MovimentoMagazzinoTM.QTA_MOVIMENTO));
					dato.put("id_lotto", lotto.getIdLotto());
					datiPagina.put(dato);
				}
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}finally {
				try {
					if(ps != null) {
						ps.close();
					}
				} catch (SQLException e) {
					e.printStackTrace(Trace.excStream);
				}
			}
		}
		return datiPagina;

	}



	@SuppressWarnings("rawtypes")
	protected List recuperaLotti(String idFornitore,String tipoCertificato) {
		List<LottoDaRs> lotti = new ArrayList<LottoDaRs>();
		Connection connection = this.getConnectionDescriptor().getConnection();
		PreparedStatement ps = null;
		try (PreparedStatement preparedStatement = connection.prepareStatement(this.getStmtEstrazione(idFornitore, tipoCertificato, true));
				ResultSet rs = preparedStatement.executeQuery()) {
			while(rs.next()) {
				LottoDaRs lotto = new LottoDaRs();
				lotto.setIdAzienda(rs.getString(YLottoTM.ID_AZIENDA));
				lotto.setIdArticolo(rs.getString(YLottoTM.ID_ARTICOLO));
				lotto.setIdLotto(rs.getString(YLottoTM.ID_LOTTO));
				lotti.add(lotto);
			}
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			try {
				if(ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		return lotti;
	}

	protected String getStmtEstrazioneDatiPagina(String idAzienda, String idArticolo,String idLotto) {
		String stmt = null;
		stmt = "SELECT DATA_BOLLA ,NUMERO_BOLLA ,NUM_RIGA_DOC , R_LOTTO ,AF.ARTICOLO_FOR,ART.DESCR_ESTESA ,M.QTA_MOVIMENTO,L.LOTTO_ACQUISTO  "
				+ "FROM THIP.MOVIM_MAGAZ M "
				+ "LEFT OUTER JOIN THIP.ARTICOLI_FOR AF "
				+ "ON M.R_AZIENDA = AF.ID_AZIENDA  "
				+ "AND AF.R_ARTICOLO = M.R_ARTICOLO "
				+ "INNER JOIN THIP.ARTICOLI ART "
				+ "ON ART.ID_AZIENDA = M.R_AZIENDA  "
				+ "AND ART.ID_ARTICOLO = M.R_ARTICOLO "
				+ "INNER JOIN THIP.LOTTI L "
				+ "ON L.ID_AZIENDA = M.R_AZIENDA  "
				+ "AND L.ID_ARTICOLO = M.R_ARTICOLO "
				+ "AND L.ID_LOTTO = M.R_LOTTO "
				+ "WHERE R_AZIENDA = '"+idAzienda+"' "
				+ "AND M.R_ARTICOLO = '"+idArticolo+"' "
				+ "AND TP_MOV_MAGAZ IN ('"+CausaleMovimentoMagazzino.ACQUISTO+"','"+CausaleMovimentoMagazzino.GENERICO+"') AND M.DATA_BOLLA IS NOT NULL";
		return stmt;
	}

	protected String getStmtEstrazione(String idFornitore,String tipoCertificato, boolean abilitatoPortale) {
		String stmt = null;
		stmt = "SELECT * FROM "+YLottoTM.TABLE_NAME_EXT+" YLOT "
				+ "INNER JOIN "+LottoTM.TABLE_NAME+" LOT "
				+ "ON YLOT."+YLottoTM.ID_AZIENDA+" = LOT."+LottoTM.ID_AZIENDA+" "
				+ "AND YLOT."+YLottoTM.ID_ARTICOLO+" = LOT."+LottoTM.ID_ARTICOLO+"  "
				+ "AND YLOT."+YLottoTM.ID_LOTTO+" = LOT."+LottoTM.ID_LOTTO+" "
				+ "INNER JOIN "+YFornitoreAcquistoTM.TABLE_NAME_EXT+" F "
				+ "ON YLOT."+YLottoTM.ID_AZIENDA+" = F."+YFornitoreAcquistoTM.ID_AZIENDA+"  "
				+ "AND LOT."+LottoTM.R_FORNITORE+" = F."+YFornitoreAcquistoTM.ID_FORNITORE+"  "
				+ "INNER JOIN "+ArticoloTM.TABLE_NAME+" ART  "
				+ "ON ART."+ArticoloTM.ID_AZIENDA+" = YLOT."+YLottoTM.ID_AZIENDA+"  "
				+ "AND ART."+ArticoloTM.ID_ARTICOLO+" = LOT."+YLottoTM.ID_ARTICOLO+"  "
				+ "INNER JOIN "+YMicroFamigliaTM.TABLE_NAME_EXT+" MICR "
				+ "ON MICR."+YMicroFamigliaTM.ID_AZIENDA+" = YLOT."+YLottoTM.ID_AZIENDA+"  "
				+ "AND MICR."+YMicroFamigliaTM.ID_MICROFAMIGLIA+" = ART."+ArticoloTM.ID_MICROFAMIGLIA+" ";
		stmt +=  "WHERE YLOT."+YLottoTM.CERTIFICATI_DA_PORTALE+" = '"+(abilitatoPortale ? 'Y' : 'N')+"' "
				+ "AND F."+YFornitoreAcquistoTM.CERTIFICATI_DA_PORTALE+" = '"+(abilitatoPortale ? 'Y' : 'N')+"' "
				+ "AND MICR."+YMicroFamigliaTM.CERTIFICATI_DA_PORTALE+" = '"+(abilitatoPortale ? 'Y' : 'N')+"' "
				+ "AND F."+YFornitoreAcquistoTM.ID_FORNITORE+" = '"+idFornitore+"' "
				+ "AND NOT EXISTS ( "
				+ "	SELECT * FROM "+DocumentoDigitaleTM.TABLE_NAME+" WHERE "+DocumentoDigitaleTM.R_TIPO_DOC_DGT+" = '"+tipoCertificato+"' "
				+ "	AND "+DocumentoDigitaleTM.R_FORNITORE+" = F."+YFornitoreAcquistoTM.ID_FORNITORE+" "
				+ "	AND "+DocumentoDigitaleTM.R_ARTICOLO+" = YLOT."+YLottoTM.ID_ARTICOLO+"  "
				+ "	AND "+DocumentoDigitaleTM.CLASSIFICAZIONE4+" = YLOT."+YLottoTM.ID_LOTTO+"  "
				+ ")";
		return stmt;
	}

	public class DatiPagina { 

		protected String DataDDT;

		protected String NumeroDDT;

		protected String RigaDDT;

		protected String ArticoloFornitore;

		protected String Articolo;

		protected String DescrizioneEstesa;

		protected String LottoAcquisto;

		protected String Quantita;

		public String getDataDDT() {
			return DataDDT;
		}

		public void setDataDDT(String dataDDT) {
			DataDDT = dataDDT;
		}

		public String getNumeroDDT() {
			return NumeroDDT;
		}

		public void setNumeroDDT(String numeroDDT) {
			NumeroDDT = numeroDDT;
		}

		public String getRigaDDT() {
			return RigaDDT;
		}

		public void setRigaDDT(String rigaDDT) {
			RigaDDT = rigaDDT;
		}

		public String getArticoloFornitore() {
			return ArticoloFornitore;
		}

		public void setArticoloFornitore(String articoloFornitore) {
			ArticoloFornitore = articoloFornitore;
		}

		public String getArticolo() {
			return Articolo;
		}

		public void setArticolo(String articolo) {
			Articolo = articolo;
		}

		public String getDescrizioneEstesa() {
			return DescrizioneEstesa;
		}

		public void setDescrizioneEstesa(String descrizioneEstesa) {
			DescrizioneEstesa = descrizioneEstesa;
		}

		public String getLottoAcquisto() {
			return LottoAcquisto;
		}

		public void setLottoAcquisto(String lottoAcquisto) {
			LottoAcquisto = lottoAcquisto;
		}

		public String getQuantita() {
			return Quantita;
		}

		public void setQuantita(String quantita) {
			Quantita = quantita;
		}

	}

	public class LottoDaRs {

		protected String idAzienda;

		protected String idArticolo;

		protected String idLotto;

		public String getIdAzienda() {
			return idAzienda;
		}

		public void setIdAzienda(String idAzienda) {
			this.idAzienda = idAzienda;
		}

		public String getIdArticolo() {
			return idArticolo;
		}

		public void setIdArticolo(String idArticolo) {
			this.idArticolo = idArticolo;
		}

		public String getIdLotto() {
			return idLotto;
		}

		public void setIdLotto(String idLotto) {
			this.idLotto = idLotto;
		}

	}

}
