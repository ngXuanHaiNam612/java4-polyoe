package com.java4.asm2polyoe.service.impl;

import com.java4.asm2polyoe.entity.Share;
import com.java4.asm2polyoe.enums.ErrorCode;
import com.java4.asm2polyoe.exception.AppException;
import com.java4.asm2polyoe.mapper.ShareMapper;
import com.java4.asm2polyoe.service.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShareServiceImpl implements ShareService {
    
    private final ShareMapper shareMapper;

    @Override
    public List<Share> findAll() {
        try {
            List<Share> shares = shareMapper.findAll();
            if (shares.isEmpty()) {
                throw new AppException(ErrorCode.LIST_SHARE_EMPTY);
            }
            return shares;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION);
        }
    }

    @Override
    public Share findById(Long id) {
        try {
            if (id == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            
            Share share = shareMapper.findById(id);
            if (share == null) {
                throw new AppException(ErrorCode.SHARE_NOT_FOUND);
            }
            
            return share;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION);
        }
    }

    @Override
    public Share save(Share share) {
        try {
            if (share == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            
            if (share.getShareDate() == null) {
                share.setShareDate(new Date());
            }
            
            // Check if share already exists for same user and video
            List<Share> existingShares = shareMapper.findAll();
            boolean exists = existingShares.stream()
                    .anyMatch(s -> s.getUserId().equals(share.getUserId()) && 
                                 s.getVideoId().equals(share.getVideoId()) &&
                                 s.getEmails().equals(share.getEmails()));
            
            if (exists) {
                throw new AppException(ErrorCode.SHARE_ALREADY_EXISTS);
            }
            
            shareMapper.insert(share);
            return share;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION);
        }
    }

    @Override
    public Share update(Long id, Share share) {
        try {
            if (id == null || share == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            
            // Check if share exists
            Share existingShare = findById(id);
            if (existingShare == null) {
                throw new AppException(ErrorCode.SHARE_NOT_FOUND);
            }
            
            share.setId(id);
            shareMapper.update(share);
            return share;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            if (id == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            
            // Check if share exists before deleting
            Share existingShare = findById(id);
            if (existingShare == null) {
                throw new AppException(ErrorCode.SHARE_NOT_FOUND);
            }
            
            shareMapper.delete(id);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION);
        }
    }
}
