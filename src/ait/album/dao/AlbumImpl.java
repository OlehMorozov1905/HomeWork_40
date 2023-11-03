package ait.album.dao;
import ait.album.model.Photo;
import java.time.LocalDate;
import java.util.Arrays;

public class AlbumImpl implements Album {
    private Photo[] photos;
    private int size;

    public AlbumImpl(int capacity) {
        this.photos = new Photo[capacity];
    }

    @Override
    public boolean addPhoto(Photo photo) {
        if (photo == null) {
            return false;
        }
        if (size < photos.length) {
            for (int i = 0; i < size; i++) {
                if (photos[i] != null && photos[i].equals(photo)) {
                    return false;
                }
            }
            photos[size++] = photo;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removePhoto(int photoId, int albumId) {
        boolean removed = false;

        for (int i = 0; i < photos.length; i++) {
            Photo photo = photos[i];
            if (photo != null && photo.getPhotoId() == photoId && photo.getAlbumId() == albumId) {
                photos[i] = null;
                size--;
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public boolean updatePhoto(int photoId, int albumId, String url) {
        for (int i = 0; i < photos.length; i++) {
            if (photos[i] != null && photos[i].getPhotoId() == photoId && photos[i].getAlbumId() == albumId) {
                photos[i].setUrl(url);
                return true;
            }
        }
        return false;
    }

    @Override
    public Photo getPhotoFromAlbum(int photoId, int albumId) {
        for (int i = 0; i < photos.length; i++) {
            Photo photo = photos[i];
            if (photo != null && photo.getPhotoId() == photoId && photo.getAlbumId() == albumId) {
                return photo;
            }
        }
        return null;
    }

    @Override
    public Photo[] getAllPhotoFromAlbum(int albumId) {
        Photo[] result = new Photo[size];
        int index = 0;
        for (int i = 0; i < photos.length; i++) {
            Photo photo = photos[i];
            if (photo != null && photo.getAlbumId() == albumId) {
                result[index++] = photo;
            }
        }
        return Arrays.copyOf(result, index);
    }

    @Override
    public Photo[] getPhotoBetweenDate(LocalDate dateFrom, LocalDate dateTo) {
        Photo[] result = new Photo[size];
        int count = 0;
        for (int i = 0; i < size; i++) {
            Photo photo = photos[i];
            LocalDate photoDate = photo.getDate().toLocalDate();
            if (photoDate.isEqual(dateFrom) || photoDate.isEqual(dateTo) || (photoDate.isAfter(dateFrom) && photoDate.isBefore(dateTo))) {
                result[count++] = photo;
            }
        }
        if (count == 0) {
            return new Photo[0];
        }
        Photo[] finResult = new Photo[count];
        System.arraycopy(result, 0, finResult, 0, count);

        Arrays.sort(finResult, (p1, p2) -> {
            int res = Integer.compare(p1.getPhotoId(), p2.getPhotoId());
            return res != 0 ? res : Integer.compare(p1.getAlbumId(), p2.getAlbumId());
        });
        return finResult;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printPhotos() {
        for (int i = 0; i < size; i++) {
            System.out.println(photos[i]);
        }
    }
}
