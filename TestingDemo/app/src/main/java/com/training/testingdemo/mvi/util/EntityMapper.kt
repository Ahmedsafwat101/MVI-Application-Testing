package com.example.mvi.util

interface EntityMapper<Entity,DomainModel> {

    fun mapFromEntity(entity: Entity):DomainModel
    fun mapFromDomain(domainModel: DomainModel):Entity

}