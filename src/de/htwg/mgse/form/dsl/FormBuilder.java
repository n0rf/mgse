package de.htwg.mgse.form.dsl;

import de.htwg.mgse.form.model.Button;
import de.htwg.mgse.form.model.ButtonType;
import de.htwg.mgse.form.model.Checkbox;
import de.htwg.mgse.form.model.Form;
import de.htwg.mgse.form.model.Input;
import de.htwg.mgse.form.model.InputType;

public class FormBuilder {

	private FormBuilder() {
	}

	/**
	 * Generate a new form with specified unique ID.
	 * 
	 * @param formId
	 *            The unique form ID.
	 * @return A {@link FormScope} that can be used to add new form elements.
	 */
	public static FormScope form(String formId, String formName) {
		Form formular = new Form(formId, formName);
		FormScope fscope = new FormScope(formular);
		return fscope;
	}

	public static class FormScope {
		private final Form form;

		private FormScope(Form formular) {
			this.form = formular;
		}

		/**
		 * Create a new input element used for text, password or numbers.
		 * 
		 * @param inputId
		 *            unique ID of the input element.
		 * @return A {@link InputScopeLabel} used to set label of the element.
		 */
		public InputScopeLabel input(String inputId) {
			return new InputScopeLabel(this, new Input(inputId));
		}

		/**
		 * Create a new checkbox element.
		 * 
		 * @param checkboxId
		 *            unique ID of the checkbox element.
		 * @return A {@link CheckboxScopeLabel} used to set label of the
		 *         element.
		 */
		public CheckboxScopeLabel checkbox(String checkboxId) {
			return new CheckboxScopeLabel(this, new Checkbox(checkboxId));
		}

		/**
		 * Create a new button element.
		 * 
		 * @param buttonId
		 *            unique ID of the button element.
		 * @return A {@link ButtonScopeLabel} used to set label of the element.
		 */
		public ButtonScopeLabel button(String buttonId) {
			return new ButtonScopeLabel(this, new Button(buttonId));
		}

		/**
		 * Generates a {@link Form} object.
		 * 
		 * @return the generated {@link Form}.
		 */
		public Form generate() {
			return form;
		}

		public static class InputScopeLabel {
			private final FormScope fs;
			private final Input input;

			private InputScopeLabel(FormScope fs, Input input) {
				this.fs = fs;
				this.input = input;
			}

			/**
			 * Setter of label attribute of the current element.
			 * 
			 * @param label
			 *            The descriptive label of the element.
			 * @return {@link InputScopeType} used to set the type attribute of
			 *         the element.
			 */
			public InputScopeType label(String label) {
				input.setLabel(label);
				return new InputScopeType(fs, input);
			}

			public static class InputScopeType {
				private final Input input;
				private final FormScope fs;

				private InputScopeType(FormScope fs, Input input) {
					this.fs = fs;
					this.input = input;
				}

				/**
				 * Set input type attribute of the current element to password.
				 * 
				 * @return {@link InputScopeDefaultValueText} used to set the
				 *         default password of the element.
				 */
				public InputScopeDefaultValueText setTypeToPassword() {
					input.setType(InputType.PASSWORD);
					return new InputScopeDefaultValueText(fs, input);
				}

				/**
				 * Set input type attribute of the current element to text.
				 * 
				 * @return {@link InputScopeDefaultValueText} used to set the
				 *         default text of the element.
				 */
				public InputScopeDefaultValueText setTypeToText() {
					input.setType(InputType.TEXT);
					return new InputScopeDefaultValueText(fs, input);
				}

				/**
				 * Set input type attribute of the current element to integer
				 * number.
				 * 
				 * @return {@link InputScopeDefaultValueInt} used to set the
				 *         default integer number of the element.
				 */
				public InputScopeDefaultValueInt setTypeToInt() {
					input.setType(InputType.INT);
					return new InputScopeDefaultValueInt(fs, input);
				}

				public static class InputScopeDefaultValueText {
					private final FormScope fs;
					private final Input input;

					private InputScopeDefaultValueText(FormScope fs, Input input) {
						this.fs = fs;
						this.input = input;
					}

					/**
					 * Setter of the default value attribute of the current
					 * element.
					 * 
					 * @param defaultValue
					 *            The default value of the input element.
					 * @return {@link FormScope} used to add another element or
					 *         generate the form.
					 */
					public FormScope defaultValue(String defaultValue) {
						input.setDefaultValue(defaultValue);
						fs.form.addElement(input);
						return fs;
					}
				}

				public static class InputScopeDefaultValueInt {
					private final FormScope fs;
					private final Input input;

					private InputScopeDefaultValueInt(FormScope fs, Input input) {
						this.fs = fs;
						this.input = input;
					}

					/**
					 * Setter of the default value attribute of the current
					 * element.
					 * 
					 * @param defaultValue
					 *            The default value of the input element.
					 * @return {@link FormScope} used to add another element or
					 *         generate the form.
					 */
					public FormScope defaultValue(int defaultValue) {
						input.setDefaultValue(new Integer(defaultValue)
								.toString());
						fs.form.addElement(input);
						return fs;
					}
				}
			}
		}

		public static class CheckboxScopeLabel {
			private final FormScope fs;
			private final Checkbox checkbox;

			private CheckboxScopeLabel(FormScope fs, Checkbox checkbox) {
				this.fs = fs;
				this.checkbox = checkbox;
			}

			/**
			 * Setter of label attribute of the current element.
			 * 
			 * @param label
			 *            The descriptive label of the element.
			 * @return {@link CheckboxScopeChecked} used to set the checked
			 *         state of the element.
			 */
			public CheckboxScopeChecked label(String label) {
				checkbox.setLabel(label);
				return new CheckboxScopeChecked(fs, checkbox);
			}

			public static class CheckboxScopeChecked {
				private final Checkbox checkbox;
				private final FormScope fs;

				private CheckboxScopeChecked(FormScope fs, Checkbox checkbox) {
					this.fs = fs;
					this.checkbox = checkbox;
				}

				/**
				 * Setter of the checked attribute of the checkbox element.
				 * 
				 * @param checked
				 *            state of the checked attribute.
				 * @return {@link FormScope} used to add another element or
				 *         generate the form.
				 */
				public FormScope checked(boolean checked) {
					checkbox.setChecked(checked);
					fs.form.addElement(checkbox);
					return fs;
				}
			}
		}

		public static class ButtonScopeLabel {
			private final FormScope fs;
			private final Button button;

			private ButtonScopeLabel(FormScope fs, Button button) {
				this.fs = fs;
				this.button = button;
			}

			/**
			 * Setter of label attribute of the current element.
			 * 
			 * @param label
			 *            The text of the button element.
			 * @return {@link ButtonScopeType} used to set the type of the
			 *         element.
			 */
			public ButtonScopeType label(String label) {
				button.setLabel(label);
				return new ButtonScopeType(fs, button);
			}

			public static class ButtonScopeType {
				private final Button button;
				private final FormScope fs;

				private ButtonScopeType(FormScope fs, Button button) {
					this.fs = fs;
					this.button = button;
				}

				/**
				 * Setter of the type attribute of the button element.
				 * 
				 * @param type
				 *            type of the button.
				 * @return {@link FormScope} used to add another element or
				 *         generate the form.
				 */
				public FormScope type(ButtonType type) {
					button.setType(type);
					fs.form.addElement(button);
					return fs;
				}
			}
		}
	}
}
