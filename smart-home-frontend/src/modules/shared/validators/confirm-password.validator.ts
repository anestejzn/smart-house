import { FormGroup, ValidationErrors, ValidatorFn } from "@angular/forms";

export function matchPasswordsValidator(): ValidatorFn {
    return (formGroup: FormGroup): ValidationErrors | null => {
        const passwordControl = formGroup.get('passwordFormControl');
        const confirmPasswordControl = formGroup.get('passwordAgainFormControl');
        return passwordControl.value !== confirmPasswordControl.value ? {mismatch:true} : null;
  }
}

