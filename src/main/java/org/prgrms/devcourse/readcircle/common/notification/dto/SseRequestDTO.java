package org.prgrms.devcourse.readcircle.common.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.prgrms.devcourse.readcircle.common.notification.entity.NotificationType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SseRequestDTO {
    private NotificationType eventType;
    private String data;
}
