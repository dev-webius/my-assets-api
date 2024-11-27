package net.webius.myassets.annotation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.webius.myassets.annotation.FieldEqualsConstraint;
import net.webius.myassets.annotation.FieldEquals;
import net.webius.myassets.exception.InvalidFieldTypeException;

import java.lang.reflect.Field;

public class FieldEqualsConstraintValidator implements ConstraintValidator<FieldEqualsConstraint, Object> {
    @Override
    public boolean isValid(Object instance, ConstraintValidatorContext context) {
        boolean isValid = true;

        // 기본 제약 위배 비활성화
        context.disableDefaultConstraintViolation();

        Field target;
        Object sourceVal, targetVal;
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
                            throw new InvalidFieldTypeException(targetName);
                        }

                        sourceVal = source.get(instance);
                        targetVal = target.get(instance);

                        // 비교 대상과 equals() 비교
                        if ((sourceVal == null && targetVal != null)
                                || (sourceVal != null && !sourceVal.equals(targetVal))) {
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
