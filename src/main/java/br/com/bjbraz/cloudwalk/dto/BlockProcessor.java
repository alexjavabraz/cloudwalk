package br.com.bjbraz.cloudwalk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BlockProcessor {

    private List<String> lines;
    private String startedLine;
}
