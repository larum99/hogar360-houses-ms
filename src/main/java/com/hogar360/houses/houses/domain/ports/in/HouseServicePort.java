    package com.hogar360.houses.houses.domain.ports.in;

    import com.hogar360.houses.houses.domain.model.HouseModel;

    public interface HouseServicePort {
        void save(HouseModel houseModel);
    }
