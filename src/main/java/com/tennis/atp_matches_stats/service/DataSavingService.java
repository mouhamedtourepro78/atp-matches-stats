package com.tennis.atp_matches_stats.service;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;

public interface DataSavingService {
    void parseCsvToMatchs(String fileName) throws IOException, CsvException;
}