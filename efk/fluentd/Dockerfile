FROM fluent/fluentd:v1.15-1

# Use root account to use apk
USER root

# install elasticsearch plugin
RUN gem install fluent-plugin-elasticsearch && \
    gem install fluent-plugin-input-gelf
USER fluent