package com.back.service;

import com.back.DTO.PointRequestDTO;
import com.back.DTO.ResultResponseDTO;
import com.back.entity.Result;
import com.back.entity.User;
import com.back.exception.UserNotFoundException;
import com.back.repository.ResultRepository;
import com.back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.back.util.ValidData.validateCoordinate;
import static com.back.util.checkPoint.inGraph;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResultResponseDTO addResult(PointRequestDTO request) {
        Double x = request.getX();
        Double y = request.getY();
        Double r = request.getR();
        String username = request.getUsername();

        validateCoordinate(x, y, r);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        boolean check = inGraph(x, y, r);

        Result result = new Result();
        result.setX(request.getX());
        result.setY(request.getY());
        result.setR(request.getR());
        result.setCheckResult(check);
        result.setClickTime(LocalDateTime.now());
        result.setUser(user);

        Result savedResult = resultRepository.save(result);

        return mapToResponseDTO(savedResult);
    }

    @Override
    public List<ResultResponseDTO> getUserResults(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        List<Result> results = resultRepository.findByUser_id(user.getUserid());
        List<ResultResponseDTO> resultsResponse = new ArrayList<>();
        for (Result result : results) {
            resultsResponse.add(mapToResponseDTO(result));
        }
        return resultsResponse;
    }

    @Override
    @Transactional
    public void clearUserResults(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        resultRepository.deleteByUser_id(user.getUserid());
    }

    private ResultResponseDTO mapToResponseDTO(Result result) {
        ResultResponseDTO dto = new ResultResponseDTO();
        dto.setId(result.getId());
        dto.setX(result.getX());
        dto.setY(result.getY());
        dto.setR(result.getR());
        dto.setCheck(result.getCheckResult());
        dto.setClickTime(result.getClickTime());
        return dto;
    }
}
