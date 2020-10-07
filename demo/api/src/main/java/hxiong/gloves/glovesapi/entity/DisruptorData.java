package hxiong.gloves.glovesapi.entity;

import hxiong.gloves.glovesapi.service.DisruptorDataType;
import lombok.Data;

@Data
public class DisruptorData {
    private DisruptorDataType type;
    private String dataId;
    private Object data;
}
