package cn.nuofankj.myblog.dto;


/**
 * DTO-ENT转换器
 *
 * @param <S>
 * @param <T>
 */
public interface DTOConvert<S, T> {

    T toDTO(S s);

    S toENT(T t);
}
