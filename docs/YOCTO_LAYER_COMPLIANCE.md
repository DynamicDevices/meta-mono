# Yocto Project layer compliance review

This document reviews what is needed for meta-mono to meet [Yocto Project Compatible](https://www.yoctoproject.org/compatible-registration/) layer requirements. It is a working checklist for the `feature/yocto-layer-compliance` branch.

## Reference

- **Registration form:** https://www.yoctoproject.org/compatible-registration/
- **Dev manual (compatibility):** https://docs.yoctoproject.org/dev-manual/layers.html#making-sure-your-layer-is-compatible-with-yocto-project
- **yocto-check-layer:** Run from build dir: `yocto-check-layer --dependency <path-to-oe-core> -- <path-to-meta-mono>`
- **Layer index:** https://layers.openembedded.org/

---

## 1. Registration form checklist (summary)


- [ ] **1.** Submitter is YP member or submission sponsored by YP member / OpenEmbedded — We will ask OpenEmbedded to sponsor this submission by DynamicDevices.
- [x] **2.** Layer listed in OpenEmbedded Layers index — [meta-mono on Layer Index](https://layers.openembedded.org/layerindex/branch/master/layer/meta-mono/)
- [x] **3.** README with origin, maintainer, change process, dependencies/versions — README.md has Layer Dependencies, Maintainer, patch/PR policy.
- [x] **4.** SECURITY file (how/where to report security issues) — SECURITY.md added on this branch.
- [ ] **5.** Builds without errors vs OE-Core with only README-stated dependencies — Must be verified per branch (styhead, etc.).
- [ ] **6.** Do not disable/bypass core QA checks (package_qa, yocto-check-layer) — See "INSANE_SKIP and QA" below.
- [x] **7.** Network only in do_fetch, only Bitbake fetcher APIs — No recipes should fetch outside do_fetch; spot-check recipes.
- [x] **8.** Mandated dependency layers have YP Compatible status — Only `meta` (OE-Core) required; OE-Core is the YP reference base layer and has Compatible status ([layer index](https://layers.openembedded.org/layerindex/branch/master/layer/openembedded-core/)).
- [x] **9.** Layer does not change system behaviour without user opt-in — Software layer; no MACHINE/DISTRO set by default.
- [ ] **10.** Passed yocto-check-layer — Run and fix until PASS.
- [ ] ~~**11.** BSP layers follow BSP Developer's Guide — *N/A:* meta-mono is a software layer.~~
- [ ] ~~**12.** Hardware / distro / recipe metadata separated (no cross-dependency) — *N/A:* Single software layer.~~
- [ ] ~~**13.** BitBake/OE-Core as components (if build system functionality) — *N/A:* We use OE, don't patch it.~~
- [ ] ~~**14.** Patches to BitBake/OE-Core submitted upstream — *N/A:* We don't patch them.~~
- [ ] **15.** Attestation: support OE architecture, layer model, BSP format, YP aims — At submission time.
- [ ] **16.** Recommendations: kernel, toolchain, testing, resulttool — CI already runs builds/tests; document in README if needed.

---

## 2. yocto-check-layer tests (meta-mono as software layer)

meta-mono is treated as a **software** layer (recipes only). It must pass **COMMON** tests only (no BSP/DISTRO).

### 2.1 common.test_readme

- **Requirement:** README exists (case-insensitive), not empty; contains "maintainer", "patch", and at least one email.
- [x] README.md exists and contains "Maintainer(s) & Patch policy", "patch"/"Pull request", and maintainer email.

### 2.2 common.test_security

- **Requirement:** Layer (or git repo top-level) has a file named `SECURITY` or `SECURITY.*` (e.g. SECURITY.md), non-empty.
- [x] SECURITY.md added on this branch.

### 2.3 common.test_parse

- **Requirement:** `bitbake -p` runs without parse errors.
- [ ] Run with OE-Core + meta-mono (and any README-stated dependencies, e.g. meta-oe if used).

### 2.4 common.test_show_environment

- **Requirement:** `bitbake -e` runs without errors.
- [ ] Same as above.

### 2.5 common.test_world

- **Requirement:** `bitbake -S none world` succeeds.
- [ ] Must be run; can be slow and may reveal missing dependencies (e.g. meta-oe for libgdiplus/giflib).

### 2.6 common.test_world_inherit_class

- **Requirement:** Same world build with `INHERIT="yocto-check-layer"` (extra per-recipe checks).
- [ ] Run after test_world passes.

### 2.7 common.test_patches_upstream_status

- **Requirement:** Every `.patch` file has a valid [Patch Upstream Status](https://docs.yoctoproject.org/contributor-guide/recipe-style-guide.html#patch-upstream-status) (e.g. `Upstream-Status: Pending`, `Inappropriate [Yocto specific]`, `Submitted [url]`).
- [ ] Audit all patches under `recipes-*`; ensure each has a correct `Upstream-Status:` line in the header (many have it; some may use wrong case e.g. `Upstream-status` or be missing).

### 2.8 common.test_signatures

- **Requirement:** For software layers, this is often skipped by the script; if run, adding the layer must not change task signatures of other layers.
- [ ] Confirmed when running yocto-check-layer.

### 2.9 common.test_layerseries_compat

- **Requirement:** Each collection sets `LAYERSERIES_COMPAT_<collection>` (e.g. `LAYERSERIES_COMPAT_mono`).
- [x] `conf/layer.conf` has `LAYERSERIES_COMPAT_mono = "styhead"`. Consider adding more series (e.g. scarthgap, kirkstone) if this layer supports them, to match README branch table.

---

## 3. INSANE_SKIP and QA (form item 6)

The form states: *"All layers in this submission do not disable or otherwise bypass error QA checks as defined as 'core' tests in the package_qa code and the yocto-check-layer script."*

### Where we disable/bypass (full inventory)

**conf/layer.conf** — `buildpaths` only (suppress TMPDIR paths in output):

| Package | Skip |
|---------|------|
| mono-dbg | buildpaths |
| mono-libs-4.5 | buildpaths |
| msbuild | buildpaths |
| msbuild-dev | buildpaths |
| python3-clr-loader | buildpaths |
| python3-pythonnet | buildpaths |

**recipes-mono/mono/** — `dev-so` on `${PN}-libs` in each versioned .bb (e.g. mono_6.12.0.206.bb); one recipe also has `file-rdeps` on `${PN}`:

| File | Skips |
|------|--------|
| mono_6.8.0.96.bb, 6.8.0.105, 6.8.0.123, 6.10.0.104, 6.12.0.* (multiple) | `${PN}-libs`: dev-so |
| mono_6.8.0.123.bb | `${PN}`: file-rdeps |
| mono-6.xx.inc | `${PN}-gac`, `${PN}-xbuild`, `${PN}-configuration-crypto`: file-rdeps |

**recipes-mono/libgdiplus/libgdiplus-common.inc** — `${PN}`: dev-so

**recipes-mono/dotnet/dotnet.inc** — `${PN}`: already-stripped libdir staticdev textrel dev-so; `${PN}-dbg`: libdir

**recipes-mono/dotnet-helloworld/dotnet-helloworld_1.0.bb** — `${PN}`: already-stripped, staticdev, buildpaths

No other QA bypasses (e.g. no PACKAGE_QA, skip flags in other layers) found in meta-mono.

**Actions:**

- [ ] Run yocto-check-layer and see if any of these are flagged as disallowed.
- [ ] If `buildpaths` (or others) are required for Mono/.NET build layout, document the justification in README or a comment so the submission can explain a "No" on item 6 if the project still requires them.

---

## 4. Layer index

- **Requirement:** Layer must be listed in the [OpenEmbedded Layers index](https://layers.openembedded.org/).
- [x] meta-mono is listed: [layerindex/branch/master/layer/meta-mono](https://layers.openembedded.org/layerindex/branch/master/layer/meta-mono/). When applying, confirm the entry still matches repository URL, description, and layer dependencies (e.g. meta, and meta-oe if optional).

---

## 5. Suggested next steps

- [ ] **Merge this branch** after review (SECURITY.md + this doc).
- [ ] **Audit patches** for correct `Upstream-Status:` (and fix case/typos like `Upstream-status`).
- [ ] **Set up a build** with OE-Core (and meta-oe if needed) for a branch (e.g. styhead), then run:
  - `yocto-check-layer --dependency <path-to-oe-core> [--dependency <path-to-meta-oe>] -- <path-to-meta-mono>`
- [ ] **Fix any failing tests** (parse, environment, world, patches, layerseries).
- [ ] **Review INSANE_SKIP** against yocto-check-layer and package_qa; document or remove as needed.
- [ ] **Confirm layer index** entry and registration form answers (membership/sponsorship, attestations).
- [ ] **Submit** the form at https://www.yoctoproject.org/compatible-registration/ when all items are satisfied.

---

## 6. Branch changes (feature/yocto-layer-compliance)

- **Added:** `SECURITY.md` – security reporting policy.
- **Added:** `docs/YOCTO_LAYER_COMPLIANCE.md` – this review and checklist.

No changes to layer.conf, README, or recipes in this branch; those can be done in follow-up commits (e.g. LAYERSERIES_COMPAT, patch headers, README tweaks).