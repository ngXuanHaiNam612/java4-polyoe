package com.java4.asm2polyoe.service.impl;

import com.java4.asm2polyoe.entity.ViewHistory;
import com.java4.asm2polyoe.enums.ErrorCode;
import com.java4.asm2polyoe.exception.AppException;
import com.java4.asm2polyoe.mapper.ViewHistoryMapper;
import com.java4.asm2polyoe.service.ViewHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ViewHistoryServiceImpl implements ViewHistoryService {
    private final ViewHistoryMapper viewHistoryMapper;

    @Override
    public List<ViewHistory> findAll() {
        try {
            List<ViewHistory> viewHistories = viewHistoryMapper.findAll();
            if (viewHistories.isEmpty()) {
                throw new AppException(ErrorCode.LIST_VIEWHISTORY_EMPTY);
            }
            return viewHistories;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e); // Truyền e
        }
    }

    @Override
    public ViewHistory findById(Long id) {
        try {
            if (id == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            ViewHistory viewHistory = viewHistoryMapper.findById(id);
            if (viewHistory == null) {
                throw new AppException(ErrorCode.VIEWHISTORY_NOT_FOUND);
            }
            return viewHistory;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e); // Truyền e
        }
    }

    @Override
    public ViewHistory findByUserIdAndVideoId(String userId, String videoId) {
        try {
            if (userId == null || videoId == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            return viewHistoryMapper.findByUserIdAndVideoId(userId, videoId);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e); // Truyền e
        }
    }

    @Override
    public ViewHistory save(ViewHistory history) {
        try {
            if (history == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }

            if (history.getViewDate() == null) {
                history.setViewDate(new Date());
            }

            // Check if view history already exists for same user and video
            List<ViewHistory> existingHistories = viewHistoryMapper.findAll();
            boolean exists = existingHistories.stream()
                    .anyMatch(vh -> vh.getUserId().equals(history.getUserId()) &&
                            vh.getVideoId().equals(history.getVideoId()));

            if (exists) {
                throw new AppException(ErrorCode.VIEWHISTORY_ALREADY_EXISTS);
            }

            viewHistoryMapper.insert(history);
            return history;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e); // Truyền e
        }
    }

    @Override
    public ViewHistory update(Long id, ViewHistory history) {
        try {
            if (id == null || history == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            // Check if view history exists
            ViewHistory existingHistory = findById(id);
            if (existingHistory == null) {
                throw new AppException(ErrorCode.VIEWHISTORY_NOT_FOUND);
            }

            history.setId(id);
            viewHistoryMapper.update(history);
            return history;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e); // Truyền e
        }
    }

    @Override
    public void delete(Long id) {
        try {
            if (id == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            // Check if view history exists before deleting
            ViewHistory existingHistory = findById(id);
            if (existingHistory == null) {
                throw new AppException(ErrorCode.VIEWHISTORY_NOT_FOUND);
            }
            viewHistoryMapper.delete(id);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e); // Truyền e
        }
    }
}

