package com.sabrina.studynow.course.favorite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public Favorite getById(Long id) {
    	return favoriteRepository.findById(id).orElse(null);
    }

    public void save(Favorite favorite) {
        favoriteRepository.save(favorite);
    }

    @Transactional
    public void delete(Long id) {
    	favoriteRepository.deleteById(id);
    }

    public void update(Favorite favorite) {
    	favoriteRepository.save(favorite);
    }

    public List<Favorite> getAll() {
    	return favoriteRepository.findAll();
    }

    @Transactional
    public void deleteAllByCourseId(Long courseId) {
    	favoriteRepository.deleteAllByCourseId(courseId);
    }

    public Favorite getFavoriteByUserId(Long userId, Long courseId) {
        return favoriteRepository.getFavoriteByUserId(userId, courseId);
    }
}
