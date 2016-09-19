package org.cxio.aspects.datamodels;

import java.io.IOException;
import java.util.List;
import static org.cxio.aspects.datamodels.AttributesAspectUtils.*;

/**
 * This class is used to present one attribute of a network. An attribute
 * consists of a name, value(s), type, and a(n) identifier(s) of the network(s)
 * the attribute is a property of.
 *
 * @author cmzmasek
 *
 */
public final class NetworkAttributesElement extends AbstractAttributesAspectElement {

	public final static String ASPECT_NAME = "networkAttributes";

	public NetworkAttributesElement(final Long subnetwork, final String name, final List<String> values) {
		super(name, subnetwork, ATTRIBUTE_DATA_TYPE.LIST_OF_STRING, values);
	}

	public NetworkAttributesElement(final Long subnetwork, final String name, final List<String> values,
			final ATTRIBUTE_DATA_TYPE type) {
		super(name, subnetwork, type, values);
		if (!AttributesAspectUtils.isListType(type)) {
			throw new IllegalArgumentException("network attribute element '" + name
					+ "': list of values provided, but given data type is " + type.toString());
		}
	}

	public NetworkAttributesElement(final Long subnetwork, final String name, final String value,
			final ATTRIBUTE_DATA_TYPE type) {
		super(name, null, subnetwork, type, stringToArrayList(value), true);
		if (AttributesAspectUtils.isListType(type)) {
			throw new IllegalArgumentException("network attribute element '" + name
					+ "': single value provided, but given data type is " + type.toString());
		}
	}

	public NetworkAttributesElement(final Long subnetwork, final String name, final String value) {
		super(name, null, subnetwork, ATTRIBUTE_DATA_TYPE.STRING, stringToArrayList(value), true);
	}

	public NetworkAttributesElement(final Long subnetwork, final String name, final Object value) {
		super(name, null, subnetwork, AttributesAspectUtils.determineDataType(value),
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
	public final List<Long> getPropertyOf() {
		throw new NoSuchMethodError("network attributes do not have a property-of data field");
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(ASPECT_NAME);
		sb.append(": ");
		sb.append("\n");
		if (_subnetwork != null) {
			sb.append("property of network: ");
			sb.append(_subnetwork);
		}
		sb.append("\n");
		sb.append("name               : ");
		sb.append(_name);
		sb.append("\n");
		if (_is_single_value) {
			sb.append("value              : ");
			sb.append(_values.get(0));
		} else {
			sb.append("values            : ");
			sb.append(_values);
		}
		sb.append("\n");
		sb.append("type               : ");
		sb.append(_data_type.toString());
		return sb.toString();
	}

	public final static NetworkAttributesElement createInstanceWithSingleValue(final Long subnetwork, final String name,
			final String value, final ATTRIBUTE_DATA_TYPE type) {

		return new NetworkAttributesElement(subnetwork, name, DatamodelsUtil.removeParenthesis(value, type), type);
	}

	public final static NetworkAttributesElement createInstanceWithMultipleValues(final Long subnetwork,
			final String name, final String values, final ATTRIBUTE_DATA_TYPE type) throws IOException {

		return new NetworkAttributesElement(subnetwork, name, DatamodelsUtil.parseStringToStringList(values, type),
				type);
	}

}
