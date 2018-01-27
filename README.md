# SuncorpTest

This is a simple program written for the Suncorp / Sapient Razorfish coding test. It's primarily written in Kotlin, but I've included a little Java that was copied over from another project.

## Design Decisions

The specification required the app not trigger off a second download if the device was rotated. This ruled out an `AsyncTask`, as these will either need to be passed when the `Activity` is destroyed, or recreated, which will entail restarting the download.

The sensible solution would have been to use a `Service` backed up by a `Thread` to keep the download off the main UI thread. But the spec mentioned using a database, which would have added a layer of complexity.

RxJava might have been an option, but I've taken a brief look at the description on the website, and didn't want to learn a new API and a new language at the same time.

I decided to use a `SyncAdapter` backed up by a `ContentProvider`. This has been a rod to beat my own back with.

For a full-blown app, I'd argue they're the way to go, as you can leverage a lot of code to schedule updates that are in the API. But they're a singificant amount of work to put together.

It's possible that there are better solutions out there. Server comms are normally one of the first things that are done on a project, and typically written once. As a contractor I often join a team a few weeks in, so the work's completed by then.

## Criticisms

From my point of view, this kind of test several major flaws:

1. It's a huge amount of work. As a candidate, I think that anything that lasts more than a couple of hours is a disproportionate amount of effort for a first or second round of an interview process. Most of the times, I reject a company that asks me to do one.
2. It's not an interesting piece of software to write. Mostly it shows that either you can follow a bunch of tutorials (e.g. on Stack Overflow or the Android Developer site), rather than engaging in any clever engineering. For example, you've got no idea how I'd structure a class hierarchy, or even if I get how to use things like virtual functions.
3. Because it's being done in a tight timeframe, it's not going to be production ready code. I'm skipping tests, and haven't bothered adding a decent logging framework because that'd take a few more hours to implement.
4. These tests always take a lot longer than the two or three hours that I'm told they'll take. Especially when they require learning a new language to complete it. (This is my first time with Kotlin, and I don't think that I'm following conventions entirely correctly.)

You could have asked me how I'd approach the problem, and discussed trade-offs. If I sent you a link to an existing project, you'd have a rough idea of my coding style. And this wouldn't have taken a weekend to do.
