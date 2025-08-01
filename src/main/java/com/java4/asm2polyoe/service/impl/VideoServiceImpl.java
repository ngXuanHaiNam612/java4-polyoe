package com.java4.asm2polyoe.service.impl;

import com.java4.asm2polyoe.entity.Video;
import com.java4.asm2polyoe.enums.ErrorCode;
import com.java4.asm2polyoe.exception.AppException;
import com.java4.asm2polyoe.mapper.VideoMapper;
import com.java4.asm2polyoe.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final VideoMapper videoMapper;

    @Override
    public List<Video> findAll() {
        try {
            List<Video> videos = videoMapper.findAll();
            if (videos.isEmpty()) {
                throw new AppException(ErrorCode.LIST_VIDEO_EMPTY);
            }
            return videos;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e); // Truyền e
        }
    }

    @Override
    public Video findById(String id) {
        try {
            if (id == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            Video video = videoMapper.findById(id);
            if (video == null) {
                throw new AppException(ErrorCode.VIDEO_NOT_FOUND);
            }
            return video;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e); // Truyền e
        }
    }

    @Override
    public List<Video> findAllByViewsDesc() {
        try {
            List<Video> videos = videoMapper.findAllByViewsDesc();
            if (videos.isEmpty()) {
                throw new AppException(ErrorCode.LIST_VIDEO_EMPTY);
            }
            return videos;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e); // Truyền e
        }
    }

    @Override
    public Video save(Video video) {
        try {
            if (video == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            // Check if video already exists by title
            List<Video> existingVideos = videoMapper.findAll();
            boolean exists = existingVideos.stream()
                    .anyMatch(v -> v.getTitle() != null && v.getTitle().equals(video.getTitle()));
            if (exists) {
                throw new AppException(ErrorCode.VIDEO_ALREADY_EXISTS);
            }
            videoMapper.insert(video);
            return video;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e); // Truyền e
        }
    }

    @Override
    public Video update(String id, Video video) {
        try {
            if (id == null || video == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            // Check if video exists
            Video existingVideo = findById(id);
            if (existingVideo == null) {
                throw new AppException(ErrorCode.VIDEO_NOT_FOUND);
            }
            video.setId(id);
            videoMapper.update(video);
            return video;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e); // Truyền e
        }
    }

    @Override
    public void delete(String id) {
        try {
            if (id == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            // Check if video exists before deleting
            Video existingVideo = findById(id);
            if (existingVideo == null) {
                throw new AppException(ErrorCode.VIDEO_NOT_FOUND);
            }
            videoMapper.delete(id);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e); // Truyền e
        }
    }

    @Override
    public void incrementViews(String id) {
        try {
            if (id == null) {
                throw new AppException(ErrorCode.NULL_POINTER_EXCEPTION);
            }
            Video video = findById(id);
            if (video == null) {
                throw new AppException(ErrorCode.VIDEO_NOT_FOUND);
            }
            video.setViews(video.getViews() + 1);
            videoMapper.update(video); // Assuming update handles view count
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e); // Truyền e
        }
    }

    @Override
    public List<Video> getTopVideos(int page, int size) {
        // This method might need a specific mapper query for pagination and sorting
        // For now, it will just return all videos sorted by views
        try {
            List<Video> allVideos = findAll(); // Get all videos
            // Sort by views in descending order
            allVideos.sort((v1, v2) -> Long.compare(v2.getViews(), v1.getViews()));

            // Apply pagination
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, allVideos.size());

            if (startIndex >= allVideos.size()) {
                return List.of(); // Return empty list if page is out of bounds
            }

            return allVideos.subList(startIndex, endIndex);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e); // Truyền e
        }
    }

    @Override
    public List<Video> getTopActiveVideos(int page, int size) {
        try {
            List<Video> activeVideos = videoMapper.findAllActive(); // Lấy tất cả video đang hoạt động
            // Sắp xếp theo lượt xem giảm dần
            activeVideos.sort((v1, v2) -> Long.compare(v2.getViews(), v1.getViews()));

            // Áp dụng phân trang
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, activeVideos.size());

            if (startIndex >= activeVideos.size()) {
                return List.of(); // Trả về danh sách rỗng nếu trang vượt quá giới hạn
            }

            return activeVideos.subList(startIndex, endIndex);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATCH_EXCEPTION, e);
        }
    }
}

