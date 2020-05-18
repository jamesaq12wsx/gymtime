package com.jamesaq12wsx.gymtime.service;

import com.jamesaq12wsx.gymtime.service.dto.UserShortDto;

import java.security.Principal;
import java.util.List;

/**
 * @author James Lin
 * @date 2020-05-15
 */
public interface UserDataService {

    List<UserShortDto> getRecentUserByClubId(Long clubId, Principal principal);

}
