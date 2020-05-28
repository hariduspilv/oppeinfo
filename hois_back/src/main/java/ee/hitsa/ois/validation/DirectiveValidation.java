package ee.hitsa.ois.validation;

/**
 * Validation rule groups for given directive type
 */
public interface DirectiveValidation {

    interface Akad extends DirectiveValidation { }
    interface Akadk extends DirectiveValidation { }
    interface Duplikaat extends DirectiveValidation { }
    interface Eksmat extends DirectiveValidation { }
    interface Ennist extends DirectiveValidation { }
    interface Finm extends DirectiveValidation { }
    interface Immat extends DirectiveValidation { } // immatv uses same rules
    interface Indok extends DirectiveValidation { }
    interface Indoklop extends DirectiveValidation { }
    interface Lopet extends DirectiveValidation { }
    interface Muu extends DirectiveValidation { }
    interface Okava extends DirectiveValidation { }
    interface Okoorm extends DirectiveValidation { }
    interface Ovorm extends DirectiveValidation { }
    interface Valis extends DirectiveValidation { }
    interface Stiptoet extends DirectiveValidation { }
    interface Stiptoetl extends DirectiveValidation { }
    interface Kiitus extends DirectiveValidation { }
    interface Kylalis extends DirectiveValidation { }
    interface Noomi extends DirectiveValidation { }
    interface Praktik extends DirectiveValidation { }
    interface Otegevus extends DirectiveValidation { }
    interface Tugi extends DirectiveValidation { }
    interface Tugilopp extends DirectiveValidation { }
    interface Valiskatk extends DirectiveValidation { }
}
