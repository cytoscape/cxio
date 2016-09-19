package org.cxio.aspects.datamodels;

import java.util.List;

/**
 *
 * This is the base class for EdgeAttributeElement, NodeAttributeElement, and
 * NetworkAttributesElement.
 *
 * @author cmzmasek
 *
 */
public abstract class AbstractAttributesAspectElement extends AbstractAspectElement {

	/** The name of this attribute. */
	public final static String ATTR_NAME = "n";

	/** The node or edge this attribute is a property of. */
	public final static String ATTR_PROPERTY_OF = "po";

	/** The subnetwork this attribute belongs to. */
	public final static String ATTR_SUBNETWORK = "s";

	/** The data type of this attribute (either atomic or list). */
	public final static String ATTR_DATA_TYPE = "d";

	/** The value(s) of this attribute. */
	public final static String ATTR_VALUES = "v";

	protected String _name;
	protected List<Long> _property_of;
	protected Long _subnetwork;
	protected ATTRIBUTE_DATA_TYPE _data_type;
	protected List<String> _values;
	protected boolean _is_single_value;

	public AbstractAttributesAspectElement(String name, List<Long> property_of, Long subnetwork,
			ATTRIBUTE_DATA_TYPE data_type, List<String> values, boolean is_single_value) {
		super();
		this._name = name;
		this._property_of = property_of;
		this._subnetwork = subnetwork;
		this._data_type = data_type;
		this._values = values;
		this._is_single_value = is_single_value;
	}

	public AbstractAttributesAspectElement(String name, List<Long> property_of, Long subnetwork, ATTRIBUTE_DATA_TYPE data_type,
			List<String> values) {
		this(name, property_of, subnetwork, data_type, values, false);
		if (property_of != null && property_of.size() > 1) {
			throw new IllegalArgumentException("cannot create attribute with list of property_of");
		}

	}

	public AbstractAttributesAspectElement(String name, Long subnetwork, ATTRIBUTE_DATA_TYPE data_type,
			List<String> values) {
		this(name, null, subnetwork, data_type, values, false);
	}

	public AbstractAttributesAspectElement(String name, Long subnetwork, ATTRIBUTE_DATA_TYPE data_type) {
		this(name, null, subnetwork, data_type, null, false);
	}

	public AbstractAttributesAspectElement(String name, ATTRIBUTE_DATA_TYPE data_type) {
		this(name, null, null, data_type, null, false);
	}

	/**
	 * This is for getting the name of the attribute.
	 *
	 * @return the name of the attribute
	 */
	public final String getName() {
		return _name;
	}

	/**
	 * This returns a list of identifiers of the elements this attribute is a
	 * property of.
	 *
	 * @return a list of identifiers of the elements this attribute is a
	 *         property o
	 */
	public List<Long> getPropertyOf() {
		return _property_of;
	}

	/**
	 * This returns the identifier of the subnetwork this attribute belongs to.
	 *
	 * @return the identifier of the subnetwork this attribute belongs to
	 */
	public final Long getSubnetwork() {
		return _subnetwork;
	}

	/**
	 * This returns the data type of the attribute.
	 *
	 *
	 * @return the data type of the attribute
	 */
	public final ATTRIBUTE_DATA_TYPE getDataType() {
		return _data_type;
	}

	/**
	 * This returns the list values of the (list) attribute as list of Strings.
	 *
	 * @return the list values of the (list) attribute as list of Strings
	 */
	public final List<String> getValues() {
		if (isSingleValue()) {
			throw new IllegalStateException("attempt to return single value as list of values");
		}
		return _values;
	}

	/**
	 * This returns the value of the (single) attribute as String.
	 *
	 * @return the value of the (single) attribute as String
	 */
	public final String getValue() {
		if (!isSingleValue()) {
			throw new IllegalStateException("attempt to return list of values as single value");
		}
		return _values.get(0);
	}

	/**
	 * This returns true if the value of this attribute is a single value, false
	 * if it is a list of values (even if the list just contains one value).
	 *
	 *
	 * @return true if single value, false if list of values
	 */
	public final boolean isSingleValue() {
		return _is_single_value;
	}

}