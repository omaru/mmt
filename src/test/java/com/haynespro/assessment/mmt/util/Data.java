package com.haynespro.assessment.mmt.util;

import com.haynespro.assessment.mmt.api.domain.Make;
import com.haynespro.assessment.mmt.api.domain.Model;
import com.haynespro.assessment.mmt.api.domain.Type;
import java.util.List;

public class Data {
  private Data() {}

  // ---------- MAKES ----------
  public static final Make FORD = new Make(1, "Ford");
  public static final Make LINCOLN = new Make(2, "Lincoln");
  public static final Make MERCURY = new Make(3, "Mercury");

  // ---------- MODELS ----------
  public static final Model MUSTANG = new Model(123, FORD, "1", "Mustang");

  public static final Model ZEPHYR = new Model(3, LINCOLN, "C (Passenger)", "Zephyr");

  public static final Model MILAN = new Model(4, MERCURY, "C (Passenger)", "Milan");

  // ---------- TYPES ----------
  public static final Type MUSTANG_38L_2003 = new Type(3326, MUSTANG, "3.8L", "2003");
  public static final Type MUSTANG_46L_2V_2003 = new Type(3327, MUSTANG, "4.6L (2V)", "2003");
  public static final Type MUSTANG_46L_4V_2003 = new Type(3328, MUSTANG, "4.6L (4V)", "2003");
  public static final Type MUSTANG_MACH1_2003 = new Type(3329, MUSTANG, "Mach I 4.6L (4V)", "2003");

  // ---------- COLLECTIONS ----------
  public static final List<Make> ALL_MAKES = List.of(FORD, LINCOLN, MERCURY);

  public static final List<Model> MODELS_BY_FORD = List.of(MUSTANG);

  public static final List<Type> TYPES_BY_MUSTANG =
      List.of(MUSTANG_38L_2003, MUSTANG_46L_2V_2003, MUSTANG_46L_4V_2003, MUSTANG_MACH1_2003);
}
