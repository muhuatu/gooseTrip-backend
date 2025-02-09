package com.example.goosetrip.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.goosetrip.dto.Image;

@Mapper
public interface ImageDao {

	// 把上傳圖片存到資料庫
    @Insert("INSERT INTO image (image) VALUES (#{image})")
    @Options(useGeneratedKeys = true, keyProperty = "imageId")
    public int saveImage(Image image);  // 使用 Image 類型作為參數

    // 根據圖片ID取得圖片
    @Select("SELECT image FROM image WHERE image_id = #{imageId}")
    Image getImageById(@Param("imageId") int imageId);
}
