package org.maciooo.domain.drawdate;

import org.junit.jupiter.api.Test;
import org.maciooo.domain.AdjustableClock;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DrawDateFacadeTest {
    @Test
    public void should_return_next_draw_date() {
        //given
        AdjustableClock clock = new AdjustableClock(LocalDateTime.of(2025, 3, 3, 15, 4, 0).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        DrawDateFacade drawDateFacade = new DrawDateFacade(clock);
        //when
        LocalDateTime dateOfTheNextSaturday = LocalDateTime.of(2025, 3, 8, 12, 0);
        //then
        assertEquals(drawDateFacade.getNextDrawDate(), dateOfTheNextSaturday);
    }
    @Test
    public void should_return_same_saturday_if_before_noon() {
        //given
        AdjustableClock clock = new AdjustableClock(LocalDateTime.of(2025, 3, 8, 10, 0, 0).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        DrawDateFacade drawDateFacade = new DrawDateFacade(clock);
        //when
        LocalDateTime dateOfTheNextSaturday = LocalDateTime.of(2025, 3, 8, 12, 0);
        //then
        assertEquals(drawDateFacade.getNextDrawDate(), dateOfTheNextSaturday);
    }

    @Test
    public void should_return_the_next_saturday_if_after_noon() {
        //given
        AdjustableClock clock = new AdjustableClock(LocalDateTime.of(2025, 3, 8, 13, 0, 0).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        DrawDateFacade drawDateFacade = new DrawDateFacade(clock);
        //when
        LocalDateTime dateOfTheNextSaturday = LocalDateTime.of(2025, 3, 15, 12, 0);
        //then
        assertEquals(drawDateFacade.getNextDrawDate(), dateOfTheNextSaturday);
    }
    @Test
    public void should_return_the_next_saturday_if_exactly_at_draw_time() {
        //given
        AdjustableClock clock = new AdjustableClock(LocalDateTime.of(2025, 3, 8, 12, 0, 0).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        DrawDateFacade drawDateFacade = new DrawDateFacade(clock);
        //when
        LocalDateTime dateOfTheNextSaturday = LocalDateTime.of(2025, 3, 15, 12, 0);
        //then
        assertEquals(drawDateFacade.getNextDrawDate(), dateOfTheNextSaturday);
    }

}