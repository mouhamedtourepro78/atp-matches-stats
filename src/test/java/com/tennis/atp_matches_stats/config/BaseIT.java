package com.tennis.atp_matches_stats.config;

import com.tennis.atp_matches_stats.AtpMatchesStatsApplication;
import com.tennis.atp_matches_stats.repos.MatchRepository;
import com.tennis.atp_matches_stats.repos.PlayerRepository;
import com.tennis.atp_matches_stats.repos.RoleRepository;
import com.tennis.atp_matches_stats.repos.TournamentRepository;
import com.tennis.atp_matches_stats.repos.UserRepository;
import java.nio.charset.Charset;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.StreamUtils;


/**
 * Abstract base class to be extended by every IT test. Starts the Spring Boot context, with all data
 * wiped out before each test.
 */
@SpringBootTest(
        classes = AtpMatchesStatsApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@ActiveProfiles("it")
@Sql({"/data/clearAll.sql", "/data/userData.sql"})
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public abstract class BaseIT {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public MatchRepository matchRepository;

    @Autowired
    public PlayerRepository playerRepository;

    @Autowired
    public TournamentRepository tournamentRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public RoleRepository roleRepository;

    private MockHttpSession authenticatedSession = null;

    @SneakyThrows
    public String readResource(final String resourceName) {
        return StreamUtils.copyToString(getClass().getResourceAsStream(resourceName), Charset.forName("UTF-8"));
    }

    public MockHttpSession authenticatedSession() throws Exception {
        if (authenticatedSession == null) {
            final MvcResult mvcResult = mockMvc.perform(
                    SecurityMockMvcRequestBuilders.formLogin().user("email", "bootify").password("password", "Bootify!")).andReturn();
            authenticatedSession = ((MockHttpSession)mvcResult.getRequest().getSession(false));
        }
        return authenticatedSession;
    }

}
