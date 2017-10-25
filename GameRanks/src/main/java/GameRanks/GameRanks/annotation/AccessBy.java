package GameRanks.GameRanks.annotation;

import GameRanks.GameRanks.model.User;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Sajat @ annotacio, ahol jelezhetjuk, hogy kinek van joga hozzaferni
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessBy {
    User.AccessLevel[] value() default {};
}
