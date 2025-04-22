    package com.hogar360.houses.houses.domain.ports.in;

    import com.hogar360.houses.houses.domain.criteria.HouseSearchCriteria;
    import com.hogar360.houses.houses.domain.model.HouseModel;
    import com.hogar360.houses.houses.domain.utils.PageResult;

    public interface HouseServicePort {
        void save(HouseModel houseModel, String role);
        PageResult<HouseModel> searchHouses(HouseSearchCriteria criteria);
    }
