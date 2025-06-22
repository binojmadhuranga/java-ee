package com.example.acpt.demoee.service;

import com.example.acpt.demoee.dto.DoctorDto;

import java.util.List;

public interface DoctorService {


    boolean saveDoctor(DoctorDto doctorDto);

    boolean updateDoctor(int id, DoctorDto doctorDto);

    boolean deleteDoctor(int id);

    DoctorDto getDoctorById(int id);

    List<DoctorDto> getAllDoctors();





}
