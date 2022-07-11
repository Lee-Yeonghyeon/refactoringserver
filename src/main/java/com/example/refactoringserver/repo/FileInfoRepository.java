package com.example.refactoringserver.repo;

import com.example.refactoringserver.model.entity.FileInfoEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfoEntity, String> {
}

