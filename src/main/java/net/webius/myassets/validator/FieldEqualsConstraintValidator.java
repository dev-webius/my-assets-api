package net.webius.myassets.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.webius.myassets.annotation.FieldEqualsConstraint;
import net.webius.myassets.annotation.FieldEquals;
import net.webius.myassets.component.MessageSourceProvider;
import net.webius.myassets.exception.InvalidFieldType;

import java.lang.reflect.Field;

public class FieldEqualsConstraintValidator implements ConstraintValidator<FieldEqualsConstraint, Object> {
    private final MessageSourceProvider messageSourceProvider;

    public FieldEqualsConstraintValidator(MessageSourceProvider messageSourceProvider) {
        this.messageSourceProvider = messageSourceProvider;
    }

    @Override
    public boolean isValid(Object instance, ConstraintValidatorContext context) {
        boolean isValid = true;

        // 기본 제약 위배 비활성화
        context.disableDefaultConstraintViolation();

        Field target;
        try {
            Class<?> clazz = instance.getClass();
            Field[] fields = clazz.getDeclaredFields();

            for (Field source : fields) {
                source.setAccessible(true);

                // @FieldEquals 어노테이션 탐색
                if (source.isAnnotationPresent(FieldEquals.class)) {
                    for (String targetName : source.getAnnotation(FieldEquals.class).value()) {
                        target = clazz.getDeclaredField(targetName);
                        target.setAccessible(true);

                        // 비교 대상 필드와 유형이 일치하지 않는 경우
                        if (!source.getType().equals(target.getType())) {
                            throw new InvalidFieldType(messageSourceProvider.get("exception.InvalidFieldType.MismatchTypes.message"));
                        }

                        // 비교 대상과 equals() 비교
                        if (!source.get(instance).equals(target.get(instance))) {
                            isValid = false;

                            // 제약사항 위배 내용 추가
                            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                                    .addPropertyNode(source.getName() + ">" + target.getName())
                                    .addConstraintViolation();
                        }
                    }
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return isValid;
    }
}
