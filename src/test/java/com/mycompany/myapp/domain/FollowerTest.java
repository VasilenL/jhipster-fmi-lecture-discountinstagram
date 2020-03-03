package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class FollowerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Follower.class);
        Follower follower1 = new Follower();
        follower1.setId("id1");
        Follower follower2 = new Follower();
        follower2.setId(follower1.getId());
        assertThat(follower1).isEqualTo(follower2);
        follower2.setId("id2");
        assertThat(follower1).isNotEqualTo(follower2);
        follower1.setId(null);
        assertThat(follower1).isNotEqualTo(follower2);
    }
}
