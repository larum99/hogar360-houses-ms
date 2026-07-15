    package com.hogar360.houses.houses.domain.ports.in;

    import com.hogar360.houses.houses.domain.criteria.HouseSearchCriteria;
    import com.hogar360.houses.houses.domain.model.HouseModel;
    import com.hogar360.houses.houses.domain.utils.PageResult;

    import java.util.List;

    public interface HouseServicePort {
        void save(HouseModel houseModel, String role, Long userId);
        PageResult<HouseModel> searchHouses(HouseSearchCriteria criteria, String role, Long userId);
        Long findPublisherIdById(Long houseId);
        List<HouseModel> findAllByPublisherId(Long publisherId);
        List<Long> findIdsByCityIdAndSector(Long cityId, String sector);
        HouseModel findById(Long houseId);
    }
