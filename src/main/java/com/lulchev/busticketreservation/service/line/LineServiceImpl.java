package com.lulchev.busticketreservation.service.line;

import com.lulchev.busticketreservation.commons.exceptions.MissingLineException;
import com.lulchev.busticketreservation.domain.entitiy.Line;
import com.lulchev.busticketreservation.domain.model.service.LineServiceModel;
import com.lulchev.busticketreservation.repository.LineRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class LineServiceImpl implements LineService {

    private final LineRepository lineRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LineServiceImpl(LineRepository lineRepository, ModelMapper modelMapper) {
        this.lineRepository = lineRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<LineServiceModel> findAll() {
        return lineRepository.findAll().stream().map(line -> modelMapper.map(line, LineServiceModel.class))
                .collect(toList());
    }

    @Override
    public void create(LineServiceModel lineServiceModel) {
        lineRepository.saveAndFlush(modelMapper.map(lineServiceModel, Line.class));
    }

    @Override
    public void edit(String id, LineServiceModel lineServiceModel) {
        final Line line = lineRepository.findById(id)
                .orElseThrow(() -> new MissingLineException("Unable to find line by id"));
        lineRepository.saveAndFlush(modelMapper.map(lineServiceModel, Line.class));
    }

    @Override
    public void delete(String id) {
        lineRepository.deleteById(id);
    }

    @Override
    public LineServiceModel findById(String id) {
        Optional<Line> optional = lineRepository.findById(id);

        return optional.map(line -> modelMapper.map(line, LineServiceModel.class)).orElse(null);
    }
}
