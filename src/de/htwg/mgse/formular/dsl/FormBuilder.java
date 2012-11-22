package de.htwg.mgse.formular.dsl;

import de.htwg.mgse.formular.model.Formular;
import de.htwg.mgse.formular.model.Input;
import de.htwg.mgse.formular.model.InputType;
import de.htwg.mgse.formular.model.Button;
import de.htwg.mgse.formular.model.ButtonType;
import de.htwg.mgse.formular.model.Checkbox;

public class FormBuilder {

	private FormBuilder() {
	}

	public static FormScope form(String formId) {
		Formular formular = new Formular(formId);
		FormScope fscope = new FormScope(formular);
		return fscope;
	}

	public static class FormScope {
		private final Formular formular;

		private FormScope(Formular formular) {
			this.formular = formular;
		}

		public InputScopeLabel input(String inputId) {
			return new InputScopeLabel(this, new Input(inputId));
		}
		
		public CheckboxScopeLabel checkbox(String checkboxId) {
			return new CheckboxScopeLabel(this, new Checkbox(checkboxId));
		}
		
		public ButtonScopeLabel button(String buttonId) {
			return new ButtonScopeLabel(this, new Button(buttonId));
		}
		
		public Formular generate() {
			return formular;
		}

		public static class InputScopeLabel {
			private final FormScope fs;
			private final Input input;

			private InputScopeLabel(FormScope fs, Input input) {
				this.fs = fs;
				this.input = input;
			}

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

				public InputScopeDefaultValue type(InputType type) {
					input.setType(type);
					return new InputScopeDefaultValue(fs, input);
				}

				public static class InputScopeDefaultValue {
					private final FormScope fs;
					private final Input input;

					private InputScopeDefaultValue(FormScope fs, Input input) {
						this.fs = fs;
						this.input = input;
					}

					public FormScope defaultValue(String defaultValue) {
						input.setDefaultValue(defaultValue);
						fs.formular.addElement(input);
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

				public FormScope checked(boolean checked) {
					checkbox.setChecked(checked);
					fs.formular.addElement(checkbox);
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

			public CheckboxScopeChecked label(String label) {
				button.setLabel(label);
				return new CheckboxScopeChecked(fs, button);
			}

			public static class CheckboxScopeChecked {
				private final Button button;
				private final FormScope fs;

				private CheckboxScopeChecked(FormScope fs, Button button) {
					this.fs = fs;
					this.button = button;
				}

				public FormScope type(ButtonType type) {
					button.setType(type);
					fs.formular.addElement(button);
					return fs;
				}
			}
		}
	}
}
