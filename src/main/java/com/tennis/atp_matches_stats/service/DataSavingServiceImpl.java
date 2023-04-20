package com.tennis.atp_matches_stats.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.tennis.atp_matches_stats.domain.Match;
import com.tennis.atp_matches_stats.domain.Player;
import com.tennis.atp_matches_stats.domain.Tournament;
import com.tennis.atp_matches_stats.repos.MatchRepository;
import com.tennis.atp_matches_stats.repos.PlayerRepository;
import com.tennis.atp_matches_stats.repos.TournamentRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class DataSavingServiceImpl implements DataSavingService {
    private PlayerService playerService;
    private MatchService matchService;
    private MatchRepository matchRepository;
    private TournamentRepository tournamentRepository;
    private PlayerRepository playerRepository;

    public DataSavingServiceImpl(MatchRepository matchRepository, @Lazy MatchService matchService,TournamentRepository tournamentRepository, @Lazy PlayerService playerService,
                                 PlayerRepository playerRepository){
        this.playerService = playerService;
        this.matchService = matchService;
        this.matchRepository = matchRepository;
        this.tournamentRepository = tournamentRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public void parseCsvToMatchs(String fileName) throws IOException, CsvException, NumberFormatException {

        URL fileUrl = new File(fileName).toURI().toURL();
        CSVReader csvReader = new CSVReader(new FileReader(fileUrl.getFile()));
        List<String[]> records = csvReader.readAll();

        for (String[] dataLine : records) {
            Match match = new Match();
            match.setId(Long.valueOf(dataLine[0]));
            Tournament tournament = new Tournament();
            Player winner = new Player();
            Player loser = new Player();
            tournament.setId(Long.valueOf(dataLine[1]));
            tournament.setName(dataLine[2]);
            tournament.setSurface(dataLine[3]);
            tournament.setDrawSize(dataLine[4]);
            tournament.setLevel(dataLine[5]);

            String target = dataLine[6];

            DateTimeFormatter f = DateTimeFormatter.ofPattern( "yyyyMMdd" );
            LocalDate ld = LocalDate.parse(target ,f);

            tournament.setDate(ld);

            match.setTournament(tournament);
            match.setMatchNumber(dataLine[7]);
            winner.setId(Long.valueOf(dataLine[8]));
            winner.setSeed(dataLine[9]);
            winner.setEntry(dataLine[10]);
            winner.setName(dataLine[11]);
            winner.setHand(dataLine[12]);
            winner.setHeight(dataLine[13]);
            winner.setNationality(dataLine[14]);
            winner.setAge(dataLine[15]);
            winner.setWonMatchs(matchService.findAllWonMatchsByPlayerId(winner.getId()));
            winner.setLostMatchs(matchService.findAllLostMatchsByPlayerId(winner.getId()));
            match.setWinner(winner);
            loser.setId(Long.valueOf(dataLine[16]));
            loser.setSeed(dataLine[17]);
            loser.setEntry(dataLine[18]);
            loser.setName(dataLine[19]);
            loser.setHand(dataLine[20]);
            loser.setHeight(dataLine[21]);
            loser.setNationality(dataLine[22]);
            loser.setAge(dataLine[23]);
            loser.setWonMatchs(matchService.findAllWonMatchsByPlayerId(loser.getId()));
            loser.setLostMatchs(matchService.findAllLostMatchsByPlayerId(loser.getId()));
            match.setLoser(loser);
            match.setScore(dataLine[24]);
            match.setBestOf(dataLine[25]);
            match.setTour(dataLine[26]);
            match.setMinutes(dataLine[27]);
            match.setWinnerAces(dataLine[28]);
            match.setWinnerDoubleFaults(dataLine[29]);
            match.setWinnerServicePoints(dataLine[30]);
            match.setWinnerFirstServeIn(dataLine[31]);
            match.setWinnerFirstServeWon(dataLine[32]);
            match.setWinnerSecondServiceWon(dataLine[33]);
            match.setWinnerServiceGames(dataLine[34]);
            match.setWinnerSavedBreakPoints(dataLine[35]);
            match.setWinnerFacedBreakPoints(dataLine[36]);
            match.setLoserAces(dataLine[37]);
            match.setLoserDoubleFaults(dataLine[38]);
            match.setLoserServicePoints(dataLine[39]);
            match.setLoserFirstServeIn(dataLine[40]);
            match.setLoserFirstServeWon(dataLine[41]);
            match.setLoserSecondServiceWon(dataLine[42]);
            match.setLoserServiceGames(dataLine[43]);
            match.setLoserSavedBreakPoints(dataLine[44]);
            match.setLoserFacedBreakPoints(dataLine[45]);
            match.setWinnerRank(dataLine[46]);
            match.setWinnerRankPoints(dataLine[47]);
            match.setLoserRank(dataLine[48]);
            match.setLoserRankPoints(dataLine[49]);


            //       if (!findingPlayer(winner) && !findingPlayer(loser) && !findingTournament(tournament)) {
            matchRepository.save(match);
            //     }
        }
    }


    boolean findingPlayer(Player player) {
        for (Player p : playerRepository.findAll()) {
            if (p.getId().equals(player.getId())) {
                return true;
            }
        }
        return false;
    }

    boolean findingTournament(Tournament tournament) {
        for (Tournament t : tournamentRepository.findAll()) {
            if (t.getId().equals(tournament.getId())) {
                return true;
            }
        }
        return false;
    }

}