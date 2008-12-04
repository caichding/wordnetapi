/**
 * 
 */
package eu.kostia.dao;

import eu.kostia.entity.*;

/**
 * @author c.cerbo
 * 
 */
public class LexicalTypeDAO extends AbstractDAO<LexicalType, Long> {

	@Override
	protected Class<LexicalType> getEntityClass() {
		return LexicalType.class;
	}

	@Override
	public LexicalType findById(Long id) {
		LexicalType result = super.findById(id);

		return result;
	}

}
