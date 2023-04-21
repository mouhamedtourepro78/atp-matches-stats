package com.tennis.atp_matches_stats.service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.tennis.atp_matches_stats.domain.AvgStat;
import com.tennis.atp_matches_stats.domain.Match;
import com.tennis.atp_matches_stats.domain.Player;
import com.tennis.atp_matches_stats.domain.Stat;
import com.tennis.atp_matches_stats.domain.Tournament;
import com.tennis.atp_matches_stats.repos.AvgStatRepository;
import com.tennis.atp_matches_stats.repos.MatchRepository;
import com.tennis.atp_matches_stats.repos.PlayerRepository;
import com.tennis.atp_matches_stats.repos.StatRepository;
import com.tennis.atp_matches_stats.repos.TournamentRepository;

import org.aspectj.weaver.loadtime.Agent;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class DataSavingServiceImpl implements DataSavingService {
    private final StatService statService;
    private final MatchService matchService;
    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final PlayerService playerService;
    private final StatRepository statRepository;
    private final TournamentService tournamentService;
    private final TournamentRepository tournamentRepository;
    AvgStatService avgStatService;
    AvgStatRepository avgStatRepository;

    public DataSavingServiceImpl(final MatchRepository matchRepository, @Lazy final MatchService matchService, final PlayerRepository playerRepository, @Lazy final StatService statService,
                                 @Lazy final PlayerService playerService, final StatRepository statRepository, @Lazy final TournamentService tournamentService, 
                                 final TournamentRepository tournamentRepository, final AvgStatService avgStatService, AvgStatRepository avgStatRepository){
        this.statRepository = statRepository;
        this.statService = statService;
        this.matchService = matchService;
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
        this.tournamentRepository = tournamentRepository;
        this.playerService = playerService;
        this.tournamentService = tournamentService;
        this.avgStatService = avgStatService;
        this.avgStatRepository = avgStatRepository;
    }

    @Override
    public void parseCsvToMatchs(String fileName) throws IOException, CsvException, NumberFormatException {

        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build(); // custom separator
        CSVReader csvReader = new CSVReaderBuilder( 
                new FileReader(fileName))
                .withCSVParser(csvParser)   // custom CSV parser
                .withSkipLines(0)           // skip the first line, header info
                .build();

        List<String[]> records = csvReader.readAll();

        for (String[] dataLine : records) {
            Match match = new Match();
            Player winner = new Player();
            Player loser = new Player();
            Stat winnerStat = new Stat();
            Stat loserStat = new Stat();
            Tournament tournament = new Tournament(); 
            AvgStat winnerAvgStat = new AvgStat();
            AvgStat loserAvgStat = new AvgStat();

            match.setId(Long.valueOf(dataLine[0]));

             
            tournament.setId(Long.valueOf(dataLine[1]));
            tournament.setName(dataLine[2]);
            tournament.setSurface(dataLine[3]);
            tournament.setDrawSize(Integer.valueOf(dataLine[4]));
            tournament.setLevel(dataLine[5]);
            String target = dataLine[6];
            DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate ld = LocalDate.parse(target,f);
            tournament.setDate(ld);


            match.setMatchNumber(Long.valueOf(dataLine[7]));

            winner.setId(Long.valueOf(dataLine[8]));
            
            Integer winnerSeed = dataLine[9].equals("") ? 0 : Integer.valueOf(dataLine[9]);

            winnerStat.setId(Long.valueOf(dataLine[0]+dataLine[8]));

            winnerStat.setSeed(winnerSeed);

            winnerStat.setEntry(dataLine[10]);
            winner.setName(dataLine[11]);
            winner.setHand(dataLine[12]);


            Long winnerHeight = dataLine[13].equals("") ? 0 : Long.valueOf(dataLine[13]);
            winner.setHeight(winnerHeight);

            winner.setNationality(dataLine[14]);
            winner.setAge(Double.valueOf(dataLine[15]));

            winner.setWonMatchs(matchService.findAllWonMatchsByPlayerId(winner.getId()));
            winner.setLostMatchs(matchService.findAllLostMatchsByPlayerId(winner.getId()));

            Long winnerAces = dataLine[28].equals("") ? 0 : Long.valueOf(dataLine[28]);
            winnerStat.setAces(winnerAces);


            Long winnerDoubleFaults  = dataLine[29].equals("") ? 0 : Long.valueOf(dataLine[29]);
            winnerStat.setDoubleFaults(winnerDoubleFaults);


            Long winnerServicePoints = dataLine[30].equals("") ? 0 : Long.valueOf(dataLine[30]);
            winnerStat.setServicePoints(winnerServicePoints);



            winnerStat.setFirstServeIn(dataLine[31].equals("") ? 0 : Long.valueOf(dataLine[31]));


            winnerStat.setFirstServeWon(dataLine[32].equals("") ? 0 : Long.valueOf(dataLine[32]));


            winnerStat.setSecondServeWon(dataLine[33].equals("") ? 0 : Long.valueOf(dataLine[33]));


            winnerStat.setServiceGames(dataLine[34].equals("") ? 0 : Long.valueOf(dataLine[34]));

            winnerStat.setSavedBreakPoints(dataLine[35].equals("") ? 0 : Long.valueOf(dataLine[35]));

            winnerStat.setFacedBreakPoints(dataLine[36].equals("") ? 0 : Long.valueOf(dataLine[36]));

            winnerStat.setRank(dataLine[46].equals("") ? 0 : Integer.valueOf(dataLine[46]));

            winnerStat.setRankPoints(dataLine[47].equals("") ? 0 : Long.valueOf(dataLine[47]));

            loser.setId(Long.valueOf(dataLine[16]));
    
            Integer loserSeed = dataLine[17].equals("") ? 0 : Integer.valueOf(dataLine[17]);

            loserStat.setId(Long.valueOf(dataLine[0]+dataLine[16]));
          
            loserStat.setSeed(loserSeed);
            loserStat.setEntry(dataLine[18]);

            loser.setName(dataLine[19]);
            loser.setHand(dataLine[20]);    

            Long loserHeight = dataLine[21].equals("") ? 0 : Long.valueOf(dataLine[21]);
            loser.setHeight(loserHeight);

            loser.setNationality(dataLine[22]);

            loser.setAge(dataLine[23].equals("") ? 0 : Double.valueOf(dataLine[23]));
            
            loser.setWonMatchs(matchService.findAllWonMatchsByPlayerId(loser.getId()));
            loser.setLostMatchs(matchService.findAllLostMatchsByPlayerId(loser.getId()));

            Long loserAces = dataLine[37].equals("") ? 0 : Long.valueOf(dataLine[37]);
            loserStat.setAces(loserAces);

            Long loserDoubleFaults = dataLine[38].equals("") ? 0 : Long.valueOf(dataLine[38]);
            loserStat.setDoubleFaults(loserDoubleFaults);


            Long loserServicePoints = dataLine[39].equals("") ? 0 : Long.valueOf(dataLine[39]);
            loserStat.setServicePoints(loserServicePoints);

            loserStat.setFirstServeIn(dataLine[40].equals("") ? 0 : Long.valueOf(dataLine[40]));
            
            loserStat.setFirstServeWon(dataLine[41].equals("") ? 0 : Long.valueOf(dataLine[41]));


            loserStat.setSecondServeWon(dataLine[42].equals("") ? 0 : Long.valueOf(dataLine[42]));


            loserStat.setServiceGames(dataLine[43].equals("") ? 0 : Long.valueOf(dataLine[43]));

            loserStat.setSavedBreakPoints(dataLine[44].equals("") ? 0 : Long.valueOf(dataLine[44]));

            loserStat.setFacedBreakPoints(dataLine[45].equals("") ? 0 : Long.valueOf(dataLine[45]));

            loserStat.setRank(dataLine[48].equals("") ? 0 : Integer.valueOf(dataLine[48]));

            loserStat.setRankPoints(dataLine[49].equals("") ? 0 : Long.valueOf(dataLine[49]));

            match.setWinner(winner); 
            match.setLoser(loser);


            match.setTournament(tournament);

            match.setScore(dataLine[24]);
            match.setBestOf(Integer.valueOf(dataLine[25]));
            match.setMatchRound(dataLine[26]);
            match.setMinutes(dataLine[27].equals("") ? 0 : Long.valueOf(dataLine[27]));

            winner.setStats(statService.findPlayerStatsByPlayerId(winner.getId()));
            loser.setStats(statService.findPlayerStatsByPlayerId(loser.getId()));

            
            winnerStat.setPlayer(winner);
            loserStat.setPlayer(loser);

        
            statRepository.save(winnerStat);
            statRepository.save(loserStat);
            if(winner != null) 
            winnerAvgStat = avgStatService.computeAvgStatsByPlayer(winner);
            if(loser != null)
            loserAvgStat = avgStatService.computeAvgStatsByPlayer(loser);

          //  winnerAvgStat.setPlayer(winner);
           // loserAvgStat.setPlayer(loser);

            winner.setAvgStat(winnerAvgStat);
            loser.setAvgStat(loserAvgStat);

        //    avgStatRepository.save(winnerAvgStat);
       //     avgStatRepository.save(loserAvgStat);
        
            matchRepository.save(match);
    
           
        }
    }
}