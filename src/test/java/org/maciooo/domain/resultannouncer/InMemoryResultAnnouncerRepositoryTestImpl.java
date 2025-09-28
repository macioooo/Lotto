package org.maciooo.domain.resultannouncer;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

class InMemoryResultAnnouncerRepositoryTestImpl implements ResultAnnouncerRepository {

    Map<String, ResultResponse> results = new ConcurrentHashMap<>();

    @Override
    public ResultResponse findByTicketId(String ticketId) {
        return results.get(ticketId);
    }

    @Override
    public ResultResponse save(ResultResponse result) {
        results.put(result.ticketId(), result);
        return result;
    }

    @Override
    public boolean existsByTicketId(String ticketId) {
        return results.containsKey(ticketId);
    }

    @Override
    public <S extends ResultResponse> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends ResultResponse> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends ResultResponse> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends ResultResponse> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends ResultResponse> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends ResultResponse> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ResultResponse> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends ResultResponse> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends ResultResponse, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends ResultResponse> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<ResultResponse> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<ResultResponse> findAll() {
        return null;
    }

    @Override
    public List<ResultResponse> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(ResultResponse entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends ResultResponse> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<ResultResponse> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<ResultResponse> findAll(Pageable pageable) {
        return null;
    }
}
