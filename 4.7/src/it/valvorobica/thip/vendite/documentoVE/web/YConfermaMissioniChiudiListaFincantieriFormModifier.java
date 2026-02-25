package it.valvorobica.thip.vendite.documentoVE.web;

import com.thera.thermfw.base.ResourceLoader;

import it.thera.thip.base.comuniVenAcq.web.ConvalidaRegressioneModifier;

/**
 *
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 20/02/2026
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72375    20/02/2026  DSSOF3   Prima stesura
 */
public class YConfermaMissioniChiudiListaFincantieriFormModifier extends ConvalidaRegressioneModifier {

	@Override
	public String getAzioneOperazione(String azione) {
		String operazione = super.getAzioneOperazione(azione);
		if (azione.equals(YDocumentoTestataVenditaEstrattoFormActionAdapter.CONFERMA_MISSIONI_CHIUDI_LISTA_TRASF_FINCANTIERI)){
			operazione = ResourceLoader.getString(YDocumentoVenditaEstrattoFormModifier.RESOURCES_PERS, "CnfMissAutoFincant").concat(" : ");
	  	}
		return operazione;
	}
}
