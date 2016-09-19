package org.cxio.aspects.datamodels;

import java.io.IOException;
import java.util.List;
import static org.cxio.aspects.datamodels.AttributesAspectUtils.*;

/**
 * This class is used to present one attribute of a network edge. An attribute
 * consists of a name, value(s), data type (optional, if not set data type is
 * string), a(n) identifier(s) of the edges(s) the attribute is a property of,
 * and a (optional) sub-network identifier.
 *
 * @author cmzmasek
 *
 */
public final class EdgeAttributesElement extends AbstractAttributesAspectElement {

	public final static String ASPECT_NAME = "edgeAttributes";

	public EdgeAttributesElement(final Long subnetwork, final List<Long> property_of, final String name,
			final List<String> values) {
		super(name, property_of, subnetwork, ATTRIBUTE_DATA_TYPE.LIST_OF_STRING, values);
	}

	public EdgeAttributesElement(final Long subnetwork, final List<Long> property_of, final String name,
			final List<String> values, final ATTRIBUTE_DATA_TYPE type) {
		super(name, property_of, subnetwork, type, values);
		if (!AttributesAspectUtils.isListType(type)) {
			throw new IllegalArgumentException("edge attribute element '" + name
					+ "': list of values provided, but given data type is " + type.toString());
		}
	}

	public EdgeAttributesElement(final List<Long> property_of, final String name, final List<String> values,
			final ATTRIBUTE_DATA_TYPE type) {
		super(name, property_of, null, type, values);
		if (!AttributesAspectUtils.isListType(type)) {
			throw new IllegalArgumentException("edge attribute element '" + name
					+ "': list of values provided, but given data type is " + type.toString());
		}
	}

	public EdgeAttributesElement(final Long subnetwork, final List<Long> property_of, final String name,
			final String value, final ATTRIBUTE_DATA_TYPE type) {
		super(name, property_of, subnetwork, type, stringToArrayList(value), true);
		if (AttributesAspectUtils.isListType(type)) {
			throw new IllegalArgumentException("edge attribute element '" + name
					+ "': single value provided, but given data type is " + type.toString());
		}
	}

	public EdgeAttributesElement(final List<Long> property_of, final String name, final String value,
			final ATTRIBUTE_DATA_TYPE type) {
		super(name, property_of, null, type, stringToArrayList(value), true);
		if (AttributesAspectUtils.isListType(type)) {
			throw new IllegalArgumentException("edge attribute element '" + name
					+ "': single value provided, but given data type is " + type.toString());
		}
	}

	public EdgeAttributesElement(final Long subnetwork, final Long property_of, final String name,
			final List<String> values, final ATTRIBUTE_DATA_TYPE type) {
		super(name, longToArrayList(property_of), subnetwork, type, values);
		if (!AttributesAspectUtils.isListType(type)) {
			throw new IllegalArgumentException("edge attribute element '" + name
					+ "': list of values provided, but given data type is " + type.toString());
		}
	}

	public EdgeAttributesElement(final Long subnetwork, final Long property_of, final String name, final String value) {
		super(name, longToArrayList(property_of), subnetwork, ATTRIBUTE_DATA_TYPE.STRING, stringToArrayList(value),
				true);
	}

	public EdgeAttributesElement(final Long subnetwork, final Long property_of, final String name, final Object value) {
		super(name, longToArrayList(property_of), subnetwork, determineDataType(value),
				stringToArrayList(String.valueOf(value)), true);
		if (value instanceof List) {
			throw new IllegalArgumentException("constructor only applicable for singe values");
		}
	}

	public EdgeAttributesElement(final Long subnetwork, final Long property_of, final String name, final String value,
			final ATTRIBUTE_DATA_TYPE type) {
		super(name, longToArrayList(property_of), subnetwork, type, stringToArrayList(value), true);
		if (AttributesAspectUtils.isListType(type)) {
			throw new IllegalArgumentException("edge attribute element '" + name
					+ "': single value provided, but given data type is " + type.toString());
		}
	}

	public EdgeAttributesElement(final Long property_of, final String name, final List<String> values,
			final ATTRIBUTE_DATA_TYPE type) {
		super(name, longToArrayList(property_of), null, type, values);
		if (!AttributesAspectUtils.isListType(type)) {
			throw new IllegalArgumentException("edge attribute element '" + name
					+ "': list of values provided, but given data type is " + type.toString());
		}
	}

	public EdgeAttributesElement(final Long property_of, final String name, final String value,
			final ATTRIBUTE_DATA_TYPE type) {
		super(name, longToArrayList(property_of), null, type, stringToArrayList(value), true);
		if (AttributesAspectUtils.isListType(type)) {
			throw new IllegalArgumentException("edge attribute element '" + name
					+ "': single value provided, but given data type is " + type.toString());
		}
	}

	public EdgeAttributesElement(final Long property_of, final String name, final Object value) {
		super(name, longToArrayList(property_of), null, AttributesAspectUtils.determineDataType(value),
				stringToArrayList(String.valueOf(value)), true);
		if (value instanceof List) {
			throw new IllegalArgumentException("constructor only applicable for singe values");
		}
	}

	@Override
	public String getAspectName() {
		return ASPECT_NAME;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(ASPECT_NAME);
		sb.append(": ");
		sb.append("\n");
		sb.append("edges: ");
		sb.append(_property_of);
		sb.append("\n");
		if (_subnetwork != null) {
			sb.append("subnetwork       : ");
			sb.append(_subnetwork);
			sb.append("\n");
		}
		sb.append("name             : ");
		sb.append(_name);
		sb.append("\n");
		if (_is_single_value) {
			sb.append("value            : ");
			sb.append(_values.get(0));
		} else {
			sb.append("values           : ");
			sb.append(_values);
		}
		sb.append("\n");
		sb.append("data type        : ");
		sb.append(_data_type.toString());
		sb.append("\n");
		return sb.toString();
	}

	public final static EdgeAttributesElement createInstanceWithSingleValue(final Long subnetwork,
			final List<Long> property_of, final String name, final String value, final ATTRIBUTE_DATA_TYPE type) {

		return new EdgeAttributesElement(subnetwork, property_of, name, DatamodelsUtil.removeParenthesis(value, type),
				type);
	}

	public final static EdgeAttributesElement createInstanceWithSingleValue(final Long subnetwork,
			final Long property_of, final String name, final String value, final ATTRIBUTE_DATA_TYPE type) {

		return new EdgeAttributesElement(subnetwork, property_of, name, DatamodelsUtil.removeParenthesis(value, type),
				type);
	}

	public final static EdgeAttributesElement createInstanceWithMultipleValues(final Long subnetwork,
			final List<Long> property_of, final String name, final String values, final ATTRIBUTE_DATA_TYPE type)
			throws IOException {

		return new EdgeAttributesElement(subnetwork, property_of, name,
				DatamodelsUtil.parseStringToStringList(values, type), type);
	}

	public final static EdgeAttributesElement createInstanceWithMultipleValues(final Long subnetwork,
			final Long property_of, final String name, final String values, final ATTRIBUTE_DATA_TYPE type)
			throws IOException {

		return new EdgeAttributesElement(subnetwork, property_of, name,
				DatamodelsUtil.parseStringToStringList(values, type), type);
	}

}
