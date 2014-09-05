package de.tbosch.tools.timetool.utils;

import java.awt.Color;
import java.util.Map;

import javax.swing.JComponent;

import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;

/**
 * Utilities for field validation with hibernate validator.
 * 
 * @author Thomas Bosch
 */
public final class ValidationUtils {

	/**
	 * The class for holding the meta data like class type and object path.
	 * 
	 * @author thomas.bosch
	 */
	@SuppressWarnings("rawtypes")
	public static class InputFieldData {

		private Class beanClass;

		private String propertyPath;

		/**
		 * @param beanClass The type of the field data
		 * @param propertyPath The path in the object tree
		 */
		public InputFieldData(Class beanClass, String propertyPath) {
			super();
			this.beanClass = beanClass;
			this.propertyPath = propertyPath;
		}

		/**
		 * @return the beanClass
		 */
		public Class getBeanClass() {
			return beanClass;
		}

		/**
		 * @param beanClass the beanClass to set
		 */
		public void setBeanClass(Class beanClass) {
			this.beanClass = beanClass;
		}

		/**
		 * @return the propertyPath
		 */
		public String getPropertyPath() {
			return propertyPath;
		}

		/**
		 * @param propertyPath the propertyPath to set
		 */
		public void setPropertyPath(String propertyPath) {
			this.propertyPath = propertyPath;
		}

	}

	/**
	 * Utils.
	 */
	private ValidationUtils() {
		// Utils
	}

	/**
	 * Notifies all input field on an error.
	 * 
	 * @param exception The error
	 * @param inputFields The map of all known input fields
	 */
	@SuppressWarnings("rawtypes")
	public static void notifyInputFields(InvalidStateException exception, Map<InputFieldData, JComponent> inputFields) {
		InvalidValue[] invalidValues = exception.getInvalidValues();
		for (InvalidValue invalidValue : invalidValues) {
			String propertyPath = invalidValue.getPropertyPath();
			Class beanClass = invalidValue.getBeanClass();
			for (InputFieldData inputFieldData : inputFields.keySet()) {
				if (inputFieldData.getBeanClass().equals(beanClass) && inputFieldData.getPropertyPath().equals(propertyPath)) {
					JComponent component = inputFields.get(inputFieldData);
					component.setToolTipText(invalidValue.getMessage());
					component.grabFocus();
					component.setBackground(Color.RED);
				}
			}
		}
	}

}
