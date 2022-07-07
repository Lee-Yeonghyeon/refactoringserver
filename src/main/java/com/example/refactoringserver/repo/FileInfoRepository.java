package com.example.refactoringserver.repo;

import com.example.refactoringserver.entity.FileInfoEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfoEntity, String> {
}

