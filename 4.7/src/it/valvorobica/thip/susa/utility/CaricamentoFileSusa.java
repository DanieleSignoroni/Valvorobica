package it.valvorobica.thip.susa.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.SQLException;

import com.thera.thermfw.common.BusinessObjectAdapter;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.security.Authorizable;

import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.cs.ThipException;
import it.valvorobica.thip.susa.CapparioSusa;

/**
 *
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 08/08/2025
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72XXX    08/08/2025  DSSOF3   Prima stesura
 */

public class CaricamentoFileSusa extends BusinessObjectAdapter implements Authorizable {

	protected String iIdAzienda;

	protected String iTemporaryFileName;

	protected String iClassNameOrigine;

	protected File file = null;

	public CaricamentoFileSusa() {
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public String getClassNameOrigine() {
		return iClassNameOrigine;
	}

	public void setClassNameOrigine(String iClassNameOrigine) {
		this.iClassNameOrigine = iClassNameOrigine;
	}

	public String getTemporaryFileName() {
		return iTemporaryFileName;
	}

	public void setTemporaryFileName(String iTemporaryFileName) {
		this.iTemporaryFileName = iTemporaryFileName;
	}

	public String getIdAzienda() {
		return iIdAzienda;
	}

	public void setIdAzienda(String iIdAzienda) {
		this.iIdAzienda = iIdAzienda;
	}

	@Override
	public int save(boolean force) throws SQLException {
		this.file = new File(getTemporaryFileName());
		if(file != null && file.exists()) {
			return processaFile();
		}else {
			//file non trovato o non salvato
			throw new ThipException("File non trovato nel percorso: "+getTemporaryFileName());
		}
	}

	protected int processaFile() throws SQLException {
		if (!"CapparioSusa".equals(getClassNameOrigine())) {
			return 0; // niente da fare per altre origini
		}
		File file = new File(getTemporaryFileName());
		if (file == null || !file.exists()) {
			throw new ThipException("File non trovato nel percorso: " + getTemporaryFileName());
		}

		Charset cs = StandardCharsets.ISO_8859_1;
		int creati = 0;

		try (BufferedReader br = Files.newBufferedReader(file.toPath(), cs)) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty()) continue;

				String cap        = slice(line, 1, 7);
				String localita   = slice(line, 8, 48);
				String instrad    = slice(line, 49, 53);
				String provincia  = slice(line, 54, 55);

				CapparioSusa c = (CapparioSusa) Factory.createObject(CapparioSusa.class);
				c.setCap(cap);
				c.setLocalita(localita);
				c.setCodiceInstradamento(instrad);
				c.setIdProvincia(provincia);
				c.save();

				creati++;
			}
		} catch (IOException e) {
			throw new ThipException("Errore lettura file cappario: " + e.getMessage());
		}

		return creati;
	}

	/** Estrae sottostringa rispettando indici 1-based [da, a] inclusivi. Ritaglia spazi ai bordi. */
	private static String slice(String s, int da1BasedInclusivo, int a1BasedInclusivo) {
		if (s == null) return "";
		int start = Math.max(0, da1BasedInclusivo - 1);
		int endEx = Math.min(s.length(), a1BasedInclusivo); // end esclusivo per substring
		if (start >= endEx) return "";
		return rtrim(ltrim(s.substring(start, endEx)));
	}

	/** Trim sinistro senza toccare zeri; equivalente a .replaceFirst("^\\s+", "") */
	private static String ltrim(String v) {
		int i = 0;
		while (i < v.length() && Character.isWhitespace(v.charAt(i))) i++;
		return v.substring(i);
	}

	/** Trim destro per togliere padding spazi a destra nelle colonne fisse */
	private static String rtrim(String v) {
		int i = v.length() - 1;
		while (i >= 0 && Character.isWhitespace(v.charAt(i))) i--;
		return v.substring(0, i + 1);
	}

}