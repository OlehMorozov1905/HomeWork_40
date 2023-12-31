package ait.album.test;
import ait.album.dao.Album;
import ait.album.dao.AlbumImpl;
import ait.album.model.Photo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;

    class AlbumTest {
        Album album;
        Photo[] ph;
        LocalDateTime now = LocalDateTime.now();
        Comparator<Photo> comparator = (p1, p2) -> {
            int res = Integer.compare(p1.getPhotoId(), p2.getPhotoId());
            return res != 0 ? res : Integer.compare(p1.getAlbumId(), p2.getAlbumId());
        };

        @BeforeEach
        void setUp() {

            album = new AlbumImpl(7);
            ph = new Photo[6];
            ph[0] = new Photo(1, 1, "title1", "url1", now.minusDays(7));
            ph[1] = new Photo(1, 2, "title2", "url2", now.minusDays(7));
            ph[2] = new Photo(1, 3, "title3", "url3", now.minusDays(5));
            ph[3] = new Photo(2, 1, "title1", "url1", now.minusDays(7));
            ph[4] = new Photo(2, 4, "title4", "url4", now.minusDays(2));
            ph[5] = new Photo(1, 4, "title4", "url1", now.minusDays(2));

            for (int i = 0; i < ph.length; i++) {
                album.addPhoto(ph[i]);

            }

        }

        @Test
        void addPhoto() {
            assertFalse(album.addPhoto(null));
            assertFalse(album.addPhoto(ph[1]));
            Photo photo = new Photo(1, 5, "title5", "url5", now.minusDays(3));
            assertTrue(album.addPhoto(photo));
            assertEquals(7, album.size());
            photo = new Photo(1, 6, "title6", "url6", now.minusDays(3));
            assertFalse(album.addPhoto(photo));
        }

        @Test
        void testRemovePhoto() {
            assertFalse(album.removePhoto(5, 1));
            assertTrue(album.removePhoto(1, 1));
            assertEquals(5, album.size());
            assertNull(album.getPhotoFromAlbum(1, 1));
        }

        @Test
        void testUpdatePhoto() {
            assertTrue(album.updatePhoto(1, 1, "newUrl"));
            assertEquals("newUrl", album.getPhotoFromAlbum(1, 1).getUrl());


        }

        @Test
        void testGetPhotoFromAlbum() {
            assertEquals(ph[0], album.getPhotoFromAlbum(1, 1));
            assertNull(album.getPhotoFromAlbum(1, 5));
        }

        @Test
        void testGetAllPhotoFromAlbum() {
            Photo[] actual = album.getAllPhotoFromAlbum(2);
            Photo[] expected = {ph[3], ph[4]};
            Arrays.sort(actual, comparator);
            assertArrayEquals(expected, actual);
        }

        @Test
        void getPhotoBetweenDate() {
            LocalDate ld = LocalDate.now();
            Photo[] actual = album.getPhotoBetweenDate(ld.minusDays(5), ld.minusDays(2));
            Photo[] expected = {ph[2], ph[5], ph[4]};
            Arrays.sort(actual, comparator);
            assertArrayEquals(expected, actual);
        }

        @Test
        void size() {
            assertEquals(6, album.size());
        }
        @Test
        void printPhotos() {
            album.printPhotos();
        }
    }
