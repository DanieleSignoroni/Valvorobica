package it.valvorobica.thip.base.matricole;

import java.util.Vector;

import com.thera.thermfw.common.BaseComponentsCollection;
import com.thera.thermfw.common.ErrorMessage;

public class YMatchMatricolaArticolo extends YMatchMatricolaArticoloPO {

	public ErrorMessage checkDelete() {

		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Vector checkAll(BaseComponentsCollection components) {
		Vector errors = super.checkAll(components);

		return errors;
	}

}
