package com.sabrina.studynow.course.favorite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void add(Favorite favorite) {
        favoriteRepository.save(favorite);
    }

    public void delete(Long id) {
    	favoriteRepository.deleteById(id);
    }

    public void update(Favorite favorite) {
    	favoriteRepository.save(favorite);
    }

    public List<Favorite> getAll() {
    	return favoriteRepository.findAll();
    }

}
