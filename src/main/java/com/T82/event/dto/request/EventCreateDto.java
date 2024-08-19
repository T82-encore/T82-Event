package com.T82.event.dto.request;

import com.T82.event.domain.Event;
import com.T82.event.domain.EventInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventCreateDto {

   private LocalDateTime bookEndTime;

   private LocalDateTime eventStartTime;


   public Event toEntity(EventInfo eventInfo){
      return Event.builder()
              .eventInfo(eventInfo)
              .bookEndTime(bookEndTime.plusHours(9))
              .eventStartTime(eventStartTime.plusHours(9))
              .eventSellCount(0L)
              .isSoldOut(false)
              .build();
   }

}
