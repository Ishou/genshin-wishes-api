package com.uf.genshinwishes.dto.mihoyo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MihoyoInfoRetDTO extends MihoyoRetDTO {
    private MihoyoUserDTO data;
}
