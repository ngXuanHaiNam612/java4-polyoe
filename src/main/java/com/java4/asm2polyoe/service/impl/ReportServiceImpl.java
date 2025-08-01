package com.java4.asm2polyoe.service.impl;

import com.java4.asm2polyoe.dto.response.report.FavoriteWithUserDto;
import com.java4.asm2polyoe.dto.response.report.GeneralStatsDto;
import com.java4.asm2polyoe.dto.response.report.ShareWithUserDto;
import com.java4.asm2polyoe.dto.response.report.VideoFavoriteStatsDto;
import com.java4.asm2polyoe.entity.Favorite;
import com.java4.asm2polyoe.entity.Share;
import com.java4.asm2polyoe.entity.User;
import com.java4.asm2polyoe.entity.Video;
import com.java4.asm2polyoe.enums.ErrorCode;
import com.java4.asm2polyoe.exception.AppException;
import com.java4.asm2polyoe.mapper.ReportMapper;
import com.java4.asm2polyoe.service.ReportService;
import com.java4.asm2polyoe.service.UserService;
import com.java4.asm2polyoe.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportMapper reportMapper;
    private final UserService userService; // Để lấy thông tin người dùng
    private final VideoService videoService; // Để lấy thông tin video

    @Override
    public List<VideoFavoriteStatsDto> getFavoriteStats() {
        try {
            List<VideoFavoriteStatsDto> stats = reportMapper.countFavoritesByVideo();
            // Thay đổi: Không ném lỗi nếu danh sách rỗng, chỉ trả về danh sách rỗng
            return stats;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    @Override
    public List<FavoriteWithUserDto> getFavoritesByVideo(String videoId) {
        try {
            if (videoId == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            // Kiểm tra xem video có tồn tại không
            Video video = videoService.findById(videoId); // Giả định videoService.findById trả về null nếu không tìm thấy hoặc ném AppException
            if (video == null) {
                throw new AppException(ErrorCode.VIDEO_NOT_FOUND);
            }

            List<Favorite> favorites = reportMapper.findFavoritesByVideoId(videoId);
            if (favorites.isEmpty()) {
                // Không ném lỗi nếu không có dữ liệu, chỉ trả về danh sách rỗng
                return List.of();
            }

            // Enrich with user data
            return favorites.stream().map(fav -> {
                User user = null;
                try {
                    user = userService.findById(fav.getUserId());
                } catch (AppException e) {
                    // Log lỗi nếu không tìm thấy người dùng nhưng không dừng luồng
                    System.err.println("User not found for favorite: " + fav.getUserId());
                }
                return FavoriteWithUserDto.builder()
                        .id(fav.getId())
                        .userId(fav.getUserId())
                        .videoId(fav.getVideoId())
                        .likeDate(fav.getLikeDate())
                        .user(user)
                        .build();
            }).collect(Collectors.toList());

        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    @Override
    public List<ShareWithUserDto> getSharesByVideo(String videoId) {
        try {
            if (videoId == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            // Kiểm tra xem video có tồn tại không
            Video video = videoService.findById(videoId);
            if (video == null) {
                throw new AppException(ErrorCode.VIDEO_NOT_FOUND);
            }

            List<Share> shares = reportMapper.findSharesByVideoId(videoId);
            if (shares.isEmpty()) {
                // Không ném lỗi nếu không có dữ liệu, chỉ trả về danh sách rỗng
                return List.of();
            }

            // Enrich with user data
            return shares.stream().map(share -> {
                User user = null;
                try {
                    user = userService.findById(share.getUserId());
                } catch (AppException e) {
                    // Log lỗi nếu không tìm thấy người dùng nhưng không dừng luồng
                    System.err.println("User not found for share: " + share.getUserId());
                }
                return ShareWithUserDto.builder()
                        .id(share.getId())
                        .userId(share.getUserId())
                        .videoId(share.getVideoId())
                        .emails(share.getEmails())
                        .shareDate(share.getShareDate())
                        .user(user)
                        .build();
            }).collect(Collectors.toList());

        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }

    @Override
    public GeneralStatsDto getGeneralStats() {
        try {
            Long totalUsers = reportMapper.countAllUsers();
            Long totalVideos = reportMapper.countAllVideos();
            Long totalFavorites = reportMapper.countAllFavorites();
            Long totalShares = reportMapper.countAllShares();
            Long totalViews = reportMapper.sumAllVideoViews();

            return GeneralStatsDto.builder()
                    .totalUsers(totalUsers)
                    .totalVideos(totalVideos)
                    .totalFavorites(totalFavorites)
                    .totalShares(totalShares)
                    .totalViews(totalViews)
                    .build();
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }
}


