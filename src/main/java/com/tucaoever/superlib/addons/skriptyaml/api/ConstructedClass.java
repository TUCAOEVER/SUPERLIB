package com.tucaoever.superlib.addons.skriptyaml.api;

import java.util.Map;

import com.tucaoever.superlib.SUPERLIB;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;


/**
 * Needed to register a custom tag.
 * 
 * @param <T>
 */
public abstract class ConstructedClass<T> extends AbstractConstruct {

	/**
	 * Prepare a value for deserialization.
	 * 
	 * @param values
	 *            the serialized map
	 * @return the deserialized data
	 */
	public abstract T construct(Map<Object, Object> values);
	
	@Override
	public Object construct(Node node) {
		final Map<Object, Object> values = SUPERLIB.getInstance().getConstructor().constructMap((MappingNode) node);
		return construct(values);
	}
}