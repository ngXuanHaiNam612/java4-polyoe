package com.java4.asm2polyoe.service.impl;

import com.java4.asm2polyoe.entity.Share;
import com.java4.asm2polyoe.entity.Video; // Import Video
import com.java4.asm2polyoe.entity.User; // Import User
import com.java4.asm2polyoe.enums.ErrorCode;
import com.java4.asm2polyoe.exception.AppException;
import com.java4.asm2polyoe.mapper.ShareMapper;
import com.java4.asm2polyoe.service.ShareService;
import com.java4.asm2polyoe.service.EmailService; // Import EmailService
import com.java4.asm2polyoe.service.UserService; // Import UserService
import com.java4.asm2polyoe.service.VideoService; // Import VideoService
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShareServiceImpl implements ShareService {

    private final ShareMapper shareMapper;
    private final EmailService emailService; // Inject EmailService
    private final UserService userService; // Inject UserService to get sender name
    private final VideoService videoService; // Inject VideoService to get video details

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
            // Truyền nguyên nhân gốc của ngoại lệ
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
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
            // Truyền nguyên nhân gốc của ngoại lệ
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    @Override
    public Share findByUserIdAndVideoIdAndEmails(String userId, String videoId, String emails) {
        try {
            if (userId == null || videoId == null || emails == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            return shareMapper.findByUserIdAndVideoIdAndEmails(userId, videoId, emails);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    @Override
    public Share save(Share share) {
        try {
            if (share == null || share.getUserId() == null || share.getVideoId() == null || share.getEmails() == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }

            if (share.getShareDate() == null) {
                share.setShareDate(new Date());
            }

            // Kiểm tra xem share đã tồn tại chưa bằng phương thức mới
            // Điều này sẽ ngăn chặn việc chia sẻ CÙNG MỘT VIDEO đến CÙNG MỘT EMAIL bởi CÙNG MỘT NGƯỜI DÙNG nhiều lần.
            // Nếu bạn muốn cho phép chia sẻ lại cùng một video đến cùng một email, bạn có thể bỏ qua hoặc thay đổi logic này.
            Share existingShare = findByUserIdAndVideoIdAndEmails(share.getUserId(), share.getVideoId(), share.getEmails());
            if (existingShare != null) {
                throw new AppException(ErrorCode.SHARE_ALREADY_EXISTS);
            }

            shareMapper.insert(share);

            // Gửi email thông báo sau khi lưu thành công
            try {
                User sender = userService.findById(share.getUserId());
                Video sharedVideo = videoService.findById(share.getVideoId());

                if (sender != null && sharedVideo != null) {
                    String senderName = sender.getFullname() != null && !sender.getFullname().isEmpty() ? sender.getFullname() : sender.getId();
                    String videoUrl = "http://localhost:3000/video/" + sharedVideo.getId(); // Điều chỉnh URL nếu frontend của bạn chạy ở địa chỉ khác
                    emailService.sendShareNotification(share.getEmails(), senderName, sharedVideo.getTitle(), sharedVideo.getDescription(), videoUrl);
                } else {
                    System.err.println("Could not send share email: Sender or Video not found.");
                }
            } catch (Exception emailEx) {
                System.err.println("Error sending share email: " + emailEx.getMessage());
                // Bạn có thể quyết định ném lại lỗi ở đây nếu việc gửi email là cực kỳ quan trọng
                // throw new AppException(ErrorCode.UNCATCH_EXCEPTION, emailEx);
            }

            return share; // Đối tượng share giờ đã chứa ID được tạo
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            // Truyền nguyên nhân gốc của ngoại lệ
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    @Override
    public Share update(Long id, Share share) {
        try {
            if (id == null || share == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            // Kiểm tra xem share có tồn tại không
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
            // Truyền nguyên nhân gốc của ngoại lệ
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            if (id == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            // Kiểm tra xem share có tồn tại không trước khi xóa
            Share existingShare = findById(id);
            if (existingShare == null) {
                throw new AppException(ErrorCode.SHARE_NOT_FOUND);
            }
            shareMapper.delete(id);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            // Truyền nguyên nhân gốc của ngoại lệ
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }
}


