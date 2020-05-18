package com.jamesaq12wsx.gymtime.base;

import java.util.List;

/**
 *
 * @param <D> Dto class
 * @param <E> Entity class
 */
public interface BaseMapper<D,E> {

    /**
     * Dto to Entity
     * @param dto
     * @return
     */
    E toEntity(D dto);

    /**
     * Entity to Dto
     * @param entity
     * @return
     */
    D toDto(E entity);

    /**
     * Dto list to Entity List
     * @param dtoList
     * @return
     */
    List<E> toEntity(List<D> dtoList);

    /**
     * Entity list to dto list
     * @param entityList
     * @return
     */
    List<D> toDto(List<E> entityList);
}
