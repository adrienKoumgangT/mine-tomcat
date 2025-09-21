package com.github.adrien.koumgang.minetomcat.lib.service;

import com.github.adrien.koumgang.minetomcat.lib.utils.ToString;

import java.util.*;

public final class Value {

    private final String s;
    private final String n;
    private final Byte b;

    private final Boolean bool;
    private final Boolean nul;

    private final List<String> ss;
    private final List<String> ns;
    private final List<Byte> bs;

    private final Map<String, String> sm;
    private final Map<String, String> nm;
    private final Map<String, Byte> bm;

    private Value(Builder builder) {
        this.s = builder.s();
        this.n = builder.n();
        this.b = builder.b();
        this.bool = builder.bool();
        this.nul = builder.nul();

        this.ss = Collections.unmodifiableList(builder.ss());
        this.ns = Collections.unmodifiableList(builder.ns());
        this.bs = Collections.unmodifiableList(builder.bs());

        this.sm = Collections.unmodifiableMap(builder.sm());
        this.nm = Collections.unmodifiableMap(builder.nm());
        this.bm = Collections.unmodifiableMap(builder.bm());
    }

    public String s() {
        return this.s;
    }

    public String n() {
        return this.n;
    }

    public Byte b() {
        return this.b;
    }

    public Boolean bool() {
        return this.bool;
    }

    public Boolean nul() {
        return this.nul;
    }

    public List<String> ss() {
        return this.ss;
    }

    public List<String> ns() {
        return this.ns;
    }

    public List<Byte> bs() {
        return this.bs;
    }

    public Map<String, String> sm() {
        return this.sm;
    }

    public Map<String, String> nm() {
        return this.nm;
    }

    public Map<String, Byte> bm() {
        return this.bm;
    }


    public <T> Optional<T> as(String fieldName, Class<T> clazz) {
        return switch (fieldName) {
            case "S" -> Optional.ofNullable(clazz.cast(this.s()));
            case "N" -> Optional.ofNullable(clazz.cast(this.n()));
            case "B" -> Optional.ofNullable(clazz.cast(this.b()));
            case "BOOL" -> Optional.ofNullable(clazz.cast(this.bool()));
            case "NUL" -> Optional.ofNullable(clazz.cast(this.nul()));
            case "SS" -> Optional.ofNullable(clazz.cast(this.ss()));
            case "NS" -> Optional.ofNullable(clazz.cast(this.ns()));
            case "BS" -> Optional.ofNullable(clazz.cast(this.bs()));
            case "SM" -> Optional.ofNullable(clazz.cast(this.sm()));
            case "NM" -> Optional.ofNullable(clazz.cast(this.nm()));
            case "BM" -> Optional.ofNullable(clazz.cast(this.bm()));
            default -> Optional.empty();
        };
    }

    @Override
    public String toString() {
        return ToString.builder("Value")
                .add("S", this.s())
                .add("N", this.n())
                .add("B", this.b())
                .add("BOOL", this.bool())
                .add("NUL", this.nul())
                .add("SS", this.ss())
                .add("NS", this.ns())
                .add("BS", this.bs())
                .add("SM", this.sm())
                .add("NM", this.nm())
                .add("BM", this.bm())
                .build();
    }

    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    public static Builder builder() {
        return new BuilderImpl();
    }


    public interface Builder {
        String s();
        Builder s(String s);

        String n();
        Builder n(String n);

        Byte b();
        Builder b(Byte b);

        Boolean bool();
        Builder bool(Boolean bool);

        Boolean nul();
        Builder nul(Boolean nul);

        List<String> ss();
        Builder ss(List<String> ss);
        Builder ss(String... ss);

        List<String> ns();
        Builder ns(List<String> ns);
        Builder ns(String... ns);

        List<Byte> bs();
        Builder bs(List<Byte> bs);
        Builder bs(Byte... bs);

        Map<String, String> sm();
        Builder sm(Map<String, String> sm);

        Map<String, String> nm();
        Builder nm(Map<String, String> nm);

        Map<String, Byte> bm();
        Builder bm(Map<String, Byte> bm);


        Value build();
    }


    static final class BuilderImpl implements Builder {
        private String s;
        private String n;
        private Byte b;

        private Boolean bool;
        private Boolean nul;

        private List<String> ss;
        private List<String> ns;
        private List<Byte> bs;

        private Map<String, String> sm;
        private Map<String, String> nm;
        private Map<String, Byte> bm;


        private BuilderImpl() {
            this.ss = Collections.emptyList();
            this.ns = Collections.emptyList();
            this.bs = Collections.emptyList();
            this.sm = Collections.emptyMap();
            this.nm = Collections.emptyMap();
            this.bm = Collections.emptyMap();
        }

        private BuilderImpl(Value model) {
            this.ss = Collections.emptyList();
            this.ns = Collections.emptyList();
            this.bs = Collections.emptyList();
            this.sm = Collections.emptyMap();
            this.nm = Collections.emptyMap();
            this.bm = Collections.emptyMap();

            this.s(model.s);
            this.n(model.n);
            this.b(model.b);
            this.bool(model.bool);
            this.nul(model.nul);
            this.ss(model.ss);
            this.ns(model.ns);
            this.bs(model.bs);
            this.bool(model.bool);
            this.nul(model.nul);
            this.sm(model.sm);
            this.nm(model.nm);
            this.bm(model.bm);
        }

        @Override
        public String s() {
            return this.s;
        }

        @Override
        public Builder s(String s) {
            this.s = s;
            return this;
        }

        @Override
        public String n() {
            return this.n;
        }

        @Override
        public Builder n(String n) {
            this.n = n;
            return this;
        }

        @Override
        public Byte b() {
            return this.b;
        }

        @Override
        public Builder b(Byte b) {
            this.b = b;
            return this;
        }

        @Override
        public Boolean bool() {
            return this.bool;
        }

        @Override
        public Builder bool(Boolean bool) {
            this.bool = bool;
            return this;
        }

        @Override
        public Boolean nul() {
            return this.nul;
        }

        @Override
        public Builder nul(Boolean nul) {
            this.nul = nul;
            return this;
        }

        @Override
        public List<String> ss() {
            return this.ss;
        }

        @Override
        public Builder ss(List<String> ss) {
            this.ss = ss != null ? ss : Collections.emptyList();
            return this;
        }

        @Override
        public Builder ss(String... ss) {
            this.ss(Arrays.asList(ss));
            return this;
        }

        @Override
        public List<String> ns() {
            return this.ns;
        }

        @Override
        public Builder ns(List<String> ns) {
            this.ns = ns != null ? ns : Collections.emptyList();
            return this;
        }

        @Override
        public Builder ns(String... ns) {
            this.ns(Arrays.asList(ns));
            return this;
        }

        @Override
        public List<Byte> bs() {
            return this.bs;
        }

        @Override
        public Builder bs(List<Byte> bs) {
            this.bs = bs != null ? bs : Collections.emptyList();
            return this;
        }

        @Override
        public Builder bs(Byte... bs) {
            this.bs(Arrays.asList(bs));
            return this;
        }

        @Override
        public Map<String, String> sm() {
            return this.sm;
        }

        @Override
        public Builder sm(Map<String, String> sm) {
            this.sm = sm != null ? sm : Collections.emptyMap();
            return this;
        }

        @Override
        public Map<String, String> nm() {
            return this.nm;
        }

        @Override
        public Builder nm(Map<String, String> nm) {
            this.nm = nm != null ? nm : Collections.emptyMap();
            return this;
        }

        @Override
        public Map<String, Byte> bm() {
            return this.bm;
        }

        @Override
        public Builder bm(Map<String, Byte> bm) {
            this.bm = bm != null ? bm : Collections.emptyMap();
            return this;
        }


        @Override
        public Value build() {
            return new Value(this);
        }

    }

}
