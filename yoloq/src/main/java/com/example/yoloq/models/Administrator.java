package com.example.yoloq.models;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Getter
@Setter
@DiscriminatorValue("ADMIN")
public class Administrator extends User{

    @OneToMany
    private Set<Banned> bans = new HashSet<>();
}
